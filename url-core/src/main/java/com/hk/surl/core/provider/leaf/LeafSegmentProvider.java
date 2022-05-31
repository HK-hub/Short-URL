package com.hk.surl.core.provider.leaf;

import com.hk.surl.core.common.LeafSegmentUtil;
import com.hk.surl.core.common.SUrlCoreExcption;
import com.hk.surl.core.provider.GenerateStrategy;
import com.hk.surl.core.generator.Generator;
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
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


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
public class LeafSegmentProvider implements GenerateStrategy {

    // 线程池服务
    public static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // TinyId 对象存储发号器配置
    private TinyId tinyIdConfig ;

    // Datasource 数据源，用来操作数据库
    private DataSource dataSource ;

    // 操作数据库
    private JdbcTemplate jdbcTemplate ;

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
    public String provideShortUrl(Generator builder) {

        // 获取下一个 id
        this.getNextId(this.tinyIdConfig.getBizType());

        // 先检查剩余率：剩余率=剩余数量/步长
        double remainderRate = (double) this.tinyIdConfig.getRemainder() / this.tinyIdConfig.getStep();
        // 剩余率: 如果低于指定剩余率则申请下一个号段
        if (remainderRate <= this.tinyIdConfig.getRemainRate()){
            this.getNextSegment();
        }

        return String.valueOf(this.tinyIdConfig.getMaxId());
    }


    /**
     * @methodName :
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

        }else{
            // 没有剩余号码可以发送, 可能是出现了数据库服务不可用，数据库号段资源耗尽
            throw new SUrlCoreExcption(SUrlCoreExcption.ExcptionInstance.LeafSegment_DB_Error);
        }

        return this.tinyIdConfig.getMaxId() ;
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
    private Integer getNextSegment(){

        // 跟新数据库
        this.tinyIdConfig.setVersion(this.tinyIdConfig.getVersion()+1);
        // begin_id = max_id
        this.tinyIdConfig.setBeginId(this.tinyIdConfig.getMaxId());
        // remainder=step
        this.tinyIdConfig.setRemainder(this.tinyIdConfig.getStep());
        // 更新时间
        this.tinyIdConfig.setUpdateTime(LocalDateTime.now());

        // 异步的去更新
        LeafSegmentProvider.executorService.submit(()->{
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
        });

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
                "set begin_id=? , max_id=? step=? , delta=?, remainder=?,update_time=?,version=?" +
                " where biz_type=? and version <= ?";

        Integer row = this.jdbcTemplate.update(sql,
                // 需要更新的值
                this.tinyIdConfig.getBeginId(), this.tinyIdConfig.getMaxId(),
                this.tinyIdConfig.getStep(), this.tinyIdConfig.getDelta(), this.tinyIdConfig.getRemainder(),
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
        List<TinyId> tinyIds = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<TinyId>());

        /*// 目标业务发号器不存在
        if (tinyIds == null || tinyIds.size() == 0) {
            throw new SUrlCoreExcption(SUrlCoreExcption.ExcptionInstance.LeafSegment_BIZ_Error);
        }*/

        return tinyIds.get(0);
    }


}
