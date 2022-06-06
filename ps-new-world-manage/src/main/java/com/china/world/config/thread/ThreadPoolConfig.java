package com.china.world.config.thread;

import com.china.world.utils.ThreadUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author Peng
 * @date 2022/3/3 15:53
 */
@Configuration
public class ThreadPoolConfig {

    private final static Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Resource
    private ThreadPoolParamConfig paramConfig;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        // 1. 实例化异步任务线程池
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 2. 设置线程池大小
        threadPoolTaskExecutor.setCorePoolSize(paramConfig.getCorePoolSize());
        // 3. 设置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(paramConfig.getMaximumPoolSize());
        // 4. 设置线程等待队列大小
        threadPoolTaskExecutor.setQueueCapacity(paramConfig.getQueueCapacity());
        // 5. 设置活跃时间：秒
        threadPoolTaskExecutor.setKeepAliveSeconds(paramConfig.getKeepAliveSeconds());
        // 6. 设置线程名字前缀
        threadPoolTaskExecutor.setThreadNamePrefix(paramConfig.getThreadNamePrefix());
        // setRejectedExecutionHandler：当线程池已经达到 max size 的时候，如何处理新任务
        // AbortPolicy          默认的拒绝策略，会 throw RejectedExecutionException 拒绝
        // CallerRunsPolicy     提交任务的主线程自己去执行该任务
        // DiscardOldestPolicy  丢弃最老的任务，其实就是把最早进入工作队列的任务丢弃，然后把新任务加入到工作队列
        // DiscardPolicy        相当大胆的策略，直接丢弃任务，没有任何异常抛出
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 7. 设置等待终止时间：秒
        threadPoolTaskExecutor.setAwaitTerminationSeconds(paramConfig.getAwaitTerminationTime());
        // 8. 进程结束前，等待线程队列中的任务执行完成
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(paramConfig.isWaitTasksComplete());
        // 9. 手动初始化线程池
        threadPoolTaskExecutor.initialize();
        logger.info("@THREAD-POOL 业务处理线程配置成功，核心线程池：[{}]，最大线程池：[{}]，队列容量：[{}]，线程名称前缀：[{}]"
                , paramConfig.getCorePoolSize(), paramConfig.getMaximumPoolSize(), paramConfig.getQueueCapacity()
                , paramConfig.getThreadNamePrefix());
        return threadPoolTaskExecutor;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    public ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(paramConfig.getCorePoolSize(),
                new BasicThreadFactory.Builder().namingPattern("SCHEDULE-POOL-%d").daemon(true).build()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                ThreadUtils.printException(r, t);
            }
        };
    }
}
