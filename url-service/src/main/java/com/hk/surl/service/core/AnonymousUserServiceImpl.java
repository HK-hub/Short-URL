package com.hk.surl.service.core;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IAnonymousUserService;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.domain.entity.VisitLog;
import com.hk.surl.domain.mapper.AnonymousUserMapper;
import com.hk.surl.domain.mapper.VisitLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @ClassName : AnonymousUserServiceImpl
 * @author : HK意境
 * @date : 2022/5/26
 * @description :匿名用户，临时用户 服务实现类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class AnonymousUserServiceImpl extends ServiceImpl<AnonymousUserMapper, AnonymousUser> implements IAnonymousUserService {

    @Resource
    private AnonymousUserMapper anonymousUserMapper;
    @Resource
    private VisitLogMapper visitLogMapper ;

    /**
     * @methodName : anonymousLogin
     * @author : HK意境
     * @date : 2022/6/21 16:29
     * @description : 匿名用户登录
     * @Todo :
     * @apiNote 根据短链接，secretKey 进行匿名用户登录
     * @params :
         * @param shortUrl 需要获取数据的短链接
         * @param secretKey 短链接颁发的secretKey
     * @return AnonymousUser
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public AnonymousUser anonymousLogin(String shortUrl, String secretKey) {

        // 查询获取数据
        AnonymousUser user = anonymousUserMapper.anonymousUserLogin(shortUrl, secretKey);

        // 判断是否存在，如果存在异步进行数据生成
        return user;
    }


    /**
     * @methodName : getTodayNewAccessVisitor
     * @author : HK意境
     * @date : 2022/6/24 19:49
     * @description : 今日新增短链接访问用户
     * @Todo :
     * @apiNote 今日新增短链接访问用户
     * @params :
         * @param shortUrl 指定短链接
     * @return List<VisitLog>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public List<VisitLog> getTodayNewAccessVisitData(String shortUrl) {

        // 构造时间区间参数
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        // 查询获取今日新增短链接访问数据
        List<VisitLog> newVisitorsByDatetime = this.visitLogMapper.getNewVisitDataByDatetime(shortUrl, startTime, endTime);

        return newVisitorsByDatetime;
    }



    /**
     * @methodName : getTodayNewAccessVisitor
     * @author : HK意境
     * @date : 2022/6/24 21:13
     * @description : 查询今日新增的访问IP
     * @Todo :
     * @apiNote 查询指定时间内的新增的独立访问IP
     * @params :
         * @param shortUrl 短链接
     * @return List<String> 独立用户IP
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public List<VisitLog> getTodayNewAccessVisitor(String shortUrl) {
        // 构造时间区间参数
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        // 查询新增今日访问数据
        List<VisitLog> visitorAccessData = this.visitLogMapper.getNewVisitDataByDatetime(shortUrl, startTime, endTime);

        // 去重获取独立IP
        List<VisitLog> aloneIps = visitorAccessData.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(VisitLog::getVisitorIp))), ArrayList::new));

        return aloneIps;
    }
}
