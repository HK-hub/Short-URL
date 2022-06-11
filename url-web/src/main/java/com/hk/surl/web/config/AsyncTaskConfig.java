package com.hk.surl.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author : HK意境
 * @ClassName : AsyncTaskConfig
 * @date : 2022/6/11 13:08
 * @description : 异步任务配置类
 * @Todo : 配置线程池，线程池销毁策略等
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Configuration
public class AsyncTaskConfig {

    // 获取cpu 核心数量
    public static Integer cpuCores = Runtime.getRuntime().availableProcessors();

    // 配置线程池的名称，调用异步任务对的时候需要指定使用的executor

    /***
     * 2、参数配置
     * 配置参数时需要考虑  CPU密集型任务 、 IO密集型任务 、内存使用率 、下游系统抗并发的能力
     *
     * 配置参数：
     * CPU密集型 CPU的核数+1
     * IO密集型 一般配置 2*CPU的核数
     * 参考公式（某大厂配置）：
     * CPU核数/(1-阻塞系数) 阻塞系数在0.8~0.9之间
     * 比如8核CPU 8/（1-0.9） = 80个线程数
     * @return
     */
    @Bean(name = "asyncTaskExecutor")
    public Executor asyncTaskExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数: 线程池创建时，初始化的线程数
        executor.setCorePoolSize(AsyncTaskConfig.cpuCores * 2);
        // 最大线程数量: 只有在阻塞队列中任务数量满了后，才会创建救急线程 : 1.5 倍系数
        executor.setMaxPoolSize(executor.getCorePoolSize() + (executor.getCorePoolSize() /2) );
        // 缓冲队列：用来缓冲执行任务的队列
        executor.setQueueCapacity(500);
        // 救急线程生存时间：允许救济线程空闲时间，超过生存时间，将会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("async-task-");
        // 拒绝策略：
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 关闭线程时，执行完全部任务才销毁
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 阻塞队列任务等待时间，超时销毁
        //executor.setAwaitTerminationSeconds(60);

        executor.initialize();

        return executor ;
    }




}
