package com.hk.surl.web.aop.processor;


import com.hk.surl.common.entity.VisitorAgent;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.common.util.IPUtil;
import com.hk.surl.common.util.LocationUtil;
import com.hk.surl.common.util.TranceIdUtil;
import com.hk.surl.domain.entity.VisitLog;
import com.hk.surl.domain.mapper.VisitLogMapper;
import com.hk.surl.domain.vo.ShortUrlVo;
import com.hk.surl.service.util.AsyncTaskService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author : HK意境
 * @ClassName : AccessProcessor
 * @date : 2022/6/14 22:53
 * @description : 短链接访问日志记录
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@Component
public class AccessProcessor extends LogProcessor{

    @Resource
    private AsyncTaskService taskService;
    @Resource
    private VisitLogMapper visitLogMapper;
    @Resource
    private LocationUtil locationUtil;


    @Override
    public Object process(ProceedingJoinPoint point,ShortUrlVo vo) throws Throwable {

        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // SystemLog
        VisitLog visitLog = this.initSysLog(point, request, vo);

        // 入库
        Boolean aBoolean = taskService.writeAccessLogToDatabase(visitLog).get();
        return new ResponseResult<ShortUrlVo>(ResultCode.SUCCESS, vo);
    }

    @Override
    public Object after(Object result) {
        return null;
    }

    // 初始化 系统日志
    private VisitLog initSysLog(ProceedingJoinPoint point, HttpServletRequest request, ShortUrlVo vo) {
        // parse method, 获取日志注解对象
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();

        // 填充属性
        VisitLog visitLog = new VisitLog();
        try{
            // 元数据
            visitLog.setShortUrl(vo.getShortUrl());
            visitLog.setLongUrl(vo.getLongUrl());
            visitLog.setCreateTime(LocalDateTime.now());
            // 访问者平台
            VisitorAgent visitorAgent = IPUtil.getUserAgent(request.getHeader("User-Agent"));
            visitorAgent.setPlatform(this.getDevice(point));
            visitLog.setEquipment(visitorAgent.toString());

            // id
            visitLog.setId(TranceIdUtil.getTraceId());
            visitLog.setMethod(request.getMethod());
            visitLog.setRequestHost(request.getRemoteHost());
            visitLog.setTargetHost(InetAddress.getLocalHost().getHostAddress());

            // reference 来源
            String referer = request.getHeader("referer");
            if (referer != null && referer.length() > 0) {
                visitLog.setReferrer(referer);
            }

            // ip ， 地区
            String ipAddress = IPUtil.getIpAddress(request);
            String cityInfo = locationUtil.getCityInfo(ipAddress);

            visitLog.setVisitorIp(ipAddress);
            visitLog.setVisitorArea(IPUtil.getCityInfo(visitLog.getVisitorIp()));
            visitLog.setUpdateTime(visitLog.getCreateTime());

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            return visitLog ;
        }
    }


    // 获取设备类型
    private String getDevice(ProceedingJoinPoint point){

        // parse method, 获取日志注解对象
        Device device = (Device) this.getParameterByName(point, "device");

        String result = "PC";
        // 获取设备型号
        if (device != null) {
            if (device.isMobile()) {
                //System.out.println("========请求来源设备是手机！========");
                result = "Mobile:";
                // 获取手机操作系统类型
                DevicePlatform devicePlatform = device.getDevicePlatform();
                switch (devicePlatform){
                    case ANDROID:
                        result += "android" ;
                        break;
                    case IOS:
                        result += "IOS";
                        break;
                    default:
                        result += "Unknown";
                        break;
                }
            } else if (device.isTablet()) {
                //System.out.println("========请求来源设备是平板！========");
                result = "tablet";
            } else if(device.isNormal()){
               // System.out.println("========请求来源设备是PC！========");
                result = "PC";
            }else {
                //System.out.println("========请求来源设备是其它！========");
                result = "other";
            }
        }

        return result ;
    }


    // 根据参数名称获取参数
    public Object getParameterByName(ProceedingJoinPoint proceedingJoinPoint, String parameterName) {
        MethodSignature methodSig = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        String[] parametersName = methodSig.getParameterNames();

        int idx = Arrays.asList(parametersName).indexOf(parameterName);

        if(args.length > idx) { // parameter exist
            return args[idx];
        } // otherwise your parameter does not exist by given name
        return null;



    }


}
