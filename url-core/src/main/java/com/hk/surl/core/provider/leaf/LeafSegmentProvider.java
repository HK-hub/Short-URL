package com.hk.surl.core.provider.leaf;

import com.hk.surl.core.common.util.LeafSegmentUtil;
import com.hk.surl.core.provider.GenerateProvider;
import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.entity.TinyId;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author : HK意境
 * @ClassName : LeafSegmentProvider
 * @date : 2022/4/18 19:40
 * @description : 美团发号器算法：Leaf-Segment
 *                  滴滴 TinyId 算法：
 *                      两种方式推荐使用Tinyid-client，这种方式ID为本地生成，号段长度(step)越长，支持的qps就越大，
 *                      如果将号段设置足够大，则qps可达1000w+。而且tinyid-client 对 tinyid-server 访问变的低频，减轻了server端的压力。
 * @Todo : 这里采用 滴滴的 TinyID 算法
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@Data
@Accessors(fluent = true)
public class LeafSegmentProvider implements GenerateProvider {

    // 线程池服务
    //public static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // TinyId 对象存储发号器配置
    private TinyId tinyIdConfig ;

    // Datasource 数据源，用来操作数据库
    private DataSource dataSource ;

    // 操作数据库
    private JdbcTemplate jdbcTemplate ;

    // 随机字符串池
    public static final String randomString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_#&%$*()+=@!~";
    public Random random = new Random();


    public LeafSegmentProvider() {
    }

    // 构造函数：开始id, 步长，增量，业务标识
    public LeafSegmentProvider(String bizType,DataSource dataSource) {
        // 设置数据源
        this.dataSource = dataSource ;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        // 根据业务标识查询得到配置信息
        this.tinyIdConfig = LeafSegmentUtil.getTinyIdConfigByBizType(bizType, dataSource);
    }

    // 构造函数：
    public LeafSegmentProvider(TinyId config, DataSource dataSource){
        this.dataSource = dataSource ;
        this.tinyIdConfig = config ;
    }


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/5/31 11:52
     * @description :
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public String provideShortUrl(ShortUrlGenerator builder) {

        // 获取下一个 id
        this.getNextId(this.tinyIdConfig.getBizType());

        // 检查是否需要扩容
        this.checkNeedCapacity();

        // 混合加密后返回
        return this.mixtureIdAndChar();
    }


    /**
     * @methodName : mixtureIdAndChar
     * @author : HK意境
     * @date : 2022/5/31 20:26
     * @description : 混合id 号 和 随机字符，保证id 号的安全性
     * @Todo : 根据 id 号的长度进行不同位置的加密
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug : 依然还是能够暴力破解
     * @Modified :
     * @Version : 1.0.0
     */
    public String mixtureIdAndChar(){

        // 0-7 位以下加三个字符，7-9位加2个随机字符，10 个以上加一个字符
        // 字符随机，添加位置随机

        StringBuilder sb = new StringBuilder(String.valueOf(this.tinyIdConfig.getMaxId()));
        int insertNum = 3;
        // 长度策略
        if (7 <= sb.length() && sb.length() <= 9){
            insertNum = 2;
        }else if (sb.length() > 9){
            insertNum = 1;
        }

        for (int i = 0; i < insertNum; i++) {
            int insertIndex = random.nextInt(sb.length());
            int cIndex = random.nextInt(LeafSegmentProvider.randomString.length());
            sb.insert(insertIndex,LeafSegmentProvider.randomString.charAt(cIndex));
        }

        return sb.toString();
    }


    /**
     * @methodName : getNextId()
     * @author : HK意境
     * @date : 2022/5/30 13:43
     * @description : 根据业务标识，获取下一个 id
     * @Todo :
     * @params :
         * @param : null
     * @return : 获取到的下一个 id
     * @throws:  数据库服务不可用异常
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    public synchronized Long getNextId(String bizType){

        // 判断缓冲区 segment 段是否还有
        if (this.tinyIdConfig.getRemainder() >= this.tinyIdConfig.getDelta()){
            // 分配下一个ID
            this.tinyIdConfig.setMaxId(this.tinyIdConfig.getMaxId()+this.tinyIdConfig.getDelta());

            // 更新配置
            this.tinyIdConfig.setRemainder(this.tinyIdConfig.getRemainder()-this.tinyIdConfig.getDelta());
            this.tinyIdConfig.setVersion(this.tinyIdConfig.getVersion()+1);
            this.tinyIdConfig.setUpdateTime(LocalDateTime.now());
        }

        // 检查是否需要扩容
        this.checkNeedCapacity();

        return this.tinyIdConfig.getMaxId() ;
    }


    /**
     * @methodName : getNextIdByBatch()
     * @author : HK意境
     * @date : 2022/5/31 17:31
     * @description : 批量获取 id 号
     * @Todo : 检查剩余量，生成批量ID
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    public List<Long> getNextIdByBatch(Integer number){

        // id 号集合
        List<Long> ids = new ArrayList<>();
        Long maxId = this.tinyIdConfig.getMaxId();
        Integer delta = this.tinyIdConfig.getDelta();

        // 如果余量充足，直接获取
        if (this.tinyIdConfig.getRemainder() >= number){
            log.info("余量充足");
            ids = this.doGetNextIdByBatch(number);

        }else if (number <= this.tinyIdConfig.getStep()){
            // 余量不够充足, 可能有两种情况：剩余余量不足，步长step 不够长
            // 这里走剩余量不足：去获取下一个号段
            log.info("余量不足，申请一个号段足够");
            Integer nextSegment = this.getNextSegment();
            ids = this.doGetNextIdByBatch(number);

        }else {
            // 这里就是 需要获取的 数量已经超过 step 长度了，需要进行循环获取
            int count = number / this.tinyIdConfig.getStep();
            int remain = number % this.tinyIdConfig.getStep();
            log.info("需要申请 {} 个段，{} 个号", count, remain);

            // 申请多个 step 号段的id 号
            for (int i = 0; i < count; i++) {
                // 先扩容
                this.getNextSegment();
                // 批量获取,批量添加
                ids.addAll(this.doGetNextIdByBatch(this.tinyIdConfig.getStep()));
            }
            // 再申请余数
            this.getNextSegment();
            ids.addAll(this.doGetNextIdByBatch(remain));
        }

        // 同步去考虑是否需要扩容
        checkNeedCapacity();

        // 返回获取到的批量ids
        return ids ;
    }


    /**
     * @methodName :checkNeedCapacity
     * @author : HK意境
     * @date : 2022/5/31 19:07
     * @description : 检查是否需要扩容，如果需要扩容就进行扩容
     * @Todo :
     * @params :
         * @param : null
     * @return : 是否进行了扩容: true:进行了扩容
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    private Boolean checkNeedCapacity(){
        // 先检查剩余率：剩余率=剩余数量/步长
        double remainderRate = (double) this.tinyIdConfig.getRemainder() / this.tinyIdConfig.getStep();
        // 剩余率: 如果低于指定剩余率则申请下一个号段
        if (remainderRate <= this.tinyIdConfig.getRemainRate()){
            this.getNextSegment();
            return true ;
        }

        return false ;
    }


    /**
     * @methodName : doGetNextIdByBatch
     * @author : HK意境
     * @date : 2022/5/31 19:10
     * @description : 批量获取id 号
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    private synchronized List<Long> doGetNextIdByBatch(int number){

        List<Long> ids = new ArrayList<>();

        // 获取当前最大 max_id
        Long maxId = this.tinyIdConfig.getMaxId();
        // 获取增量
        Integer delta = this.tinyIdConfig.getDelta();

        // 循环获取下一个id
        for (int i = 1; i <= number; i++) {
            ids.add(maxId + ((long) i * delta));
        }

        // 这个地方的更新版本不能异步跟新
        // 更新版本: max_id = max_id + number * delta ; remainder= remainder-number*delta
        this.tinyIdConfig.setMaxId(maxId + (long) number * delta);
        this.tinyIdConfig.setRemainder(this.tinyIdConfig.getRemainder()-number*delta);
        this.tinyIdConfig.setUpdateTime(LocalDateTime.now());
        this.tinyIdConfig.setVersion(this.tinyIdConfig.getVersion()+number);

        return ids ;
    }


    /**
     * @methodName : getNextSegment
     * @author : HK意境
     * @date : 2022/5/30 16:35
     * @description : 获取下一个号段
     * @Todo : 跟新数据库保存现场信息，获取新的配置信息
     * @params :
         * @param : null
     * @return : 返回更新的行数
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    private synchronized Integer getNextSegment(){

        // 跟新数据库
        this.tinyIdConfig.setVersion(this.tinyIdConfig.getVersion()+1);
        // begin_id = max_id
        this.tinyIdConfig.setBeginId(this.tinyIdConfig.getMaxId());
        // remainder=step
        this.tinyIdConfig.setRemainder(this.tinyIdConfig.getStep());
        // 更新时间
        this.tinyIdConfig.setUpdateTime(LocalDateTime.now());

        // 异步的去更新
        /*LeafSegmentProvider.executorService.submit(()->{
            Connection connection = null ;
            try {
                connection = this.jdbcTemplate.getDataSource().getConnection();
                // 开启事务
                connection.setAutoCommit(false);

                String sql = "update tb_tiny_id set begin_id=? ,remainder=?,update_time=?,version+? where biz_type=?";
                // 跟新：返回受影响的行数
                int row = this.jdbcTemplate.update(sql, this.tinyIdConfig.getBeginId(), this.tinyIdConfig.getRemainder(),
                        this.tinyIdConfig.getUpdateTime(), this.tinyIdConfig.getVersion());

                // 提交事务
                connection.commit();

            } catch (SQLException exception) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException e) {
                    }
                }
                log.info("acquire and update the next segment failure: {}", exception.getMessage());
            }
        });*/
        Connection connection = null ;
        try {
            connection = this.jdbcTemplate.getDataSource().getConnection();
            // 开启事务
            connection.setAutoCommit(false);

            String sql = "update tb_tiny_id set begin_id=?, max_id=?,remainder=?,update_time=?,version=? where biz_type=?";
            // 跟新：返回受影响的行数
            int row = this.jdbcTemplate.update(sql, this.tinyIdConfig.getBeginId(), this.tinyIdConfig.getMaxId(),this.tinyIdConfig.getRemainder(),
                    this.tinyIdConfig.getUpdateTime(), this.tinyIdConfig.getVersion(),this.tinyIdConfig.getBizType());

            // 提交事务
            connection.commit();

        } catch (SQLException exception) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                }
            }
            log.info("acquire and update the next segment failure: {}", exception.getMessage());
        }finally {
            try{
                connection.close();
            }catch(Exception e){
                e.printStackTrace();

            }
        }
        return 1;
    }


    /**
     * @methodName : changeBizType
     * @author : HK意境
     * @date : 2022/5/30 13:44
     * @description : 切换 业务标识符，更换业务到其他业务处，需要保存上下文环境，切换到新的环境
     * @Todo : 保存上下文环境，切换到新的环境
     * @params :
         * @param : String bizType: 新的业务标识符，需要切换成为的目标业务发号器
     * @return : 新的环境的 max_id
     * @throws:
     * @Bug : 这里不能异步的去保存，可能会出现上下文环境保存不完整的情况
     * @Modified :
     * @Version : 1.0.0
     */
    @Transactional(rollbackFor = Exception.class)
    public Long changeBizType(String newBizType){

        boolean enableChange = true ;

        // 保存现场信息: 乐观锁

        // 判断是否需要进行保存,先检查是否需要保存
        Long dbVersion = this.selectByBizType(this.tinyIdConfig.getBizType()).getVersion();
        // 检查是否是因为 数据库 version > this.version 造成的原因
        if (dbVersion > this.tinyIdConfig.getVersion()){
            // 如果 数据库version 是最新的，说明就不需要进行上下文保存了；
            enableChange = true ;
        }else{
            // 返回修改的行数: 保存的上下文环境
            for (int i = 0; i < 3; i++) {
                // 进行三次重试修改，
                Integer res = this.saveContext();
                if (res >= 1){
                    // 上下文保存成功
                    enableChange = true ;
                    break;
                }else{
                    // 保存失败，
                    enableChange = false ;
                }
            }
        }

        // 如果可以进行切换：上下文保存成功，自旋成功
        if(enableChange){
            // 切换上下文
            TinyId tinyId = this.selectByBizType(newBizType);
            if (tinyId != null) {
                this.tinyIdConfig = tinyId ;
            }
        }

        return this.tinyIdConfig.getMaxId();
    }


    /**
     * @methodName :saveContext
     * @author : HK意境
     * @date : 2022/5/30 15:54
     * @description : 保存当前环境上下文，发号器数据
     * @Todo :
     * @params :
         * @param : null
     * @return : 成功保存更新的行数
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized Integer saveContext(){

        // 保存当前的上下文信息
        String sql = "update tb_tiny_id " +
                "set begin_id=? , max_id=?, step=? , delta=?, remainder=? , remain_rate=?,update_time=?,version=?" +
                " where biz_type=? and version <= ?";

        Integer row = this.jdbcTemplate.update(sql,
                // 需要更新的值
                this.tinyIdConfig.getBeginId(), this.tinyIdConfig.getMaxId(), this.tinyIdConfig.getStep(),
                this.tinyIdConfig.getDelta(), this.tinyIdConfig.getRemainder(),this.tinyIdConfig.getRemainRate(),
                LocalDateTime.now(), this.tinyIdConfig.getVersion(),
                // where 条件参数
                this.tinyIdConfig.getBizType(),this.tinyIdConfig.getVersion());

        // 返回受影响的行数
        return row ;
    }


    /**
     * @methodName : selectByBizType
     * @author : HK意境
     * @date : 2022/5/30 16:05
     * @description : 查找指定业务标识的 发号器配置数据
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    private TinyId selectByBizType(String bizType){

        // 查询目标业务发号器
        String sql = "select * from tb_tiny_id where biz_type = ?";
        List<TinyId> tinyIds = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<TinyId>(TinyId.class));

        /*// 目标业务发号器不存在
        if (tinyIds == null || tinyIds.size() == 0) {
            throw new SUrlCoreExcption(SUrlCoreExcption.ExcptionInstance.LeafSegment_BIZ_Error);
        }*/

        return tinyIds.get(0);
    }


}
