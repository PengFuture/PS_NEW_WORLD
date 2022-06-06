package com.china.world.config.thread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Peng
 * @date 2022/3/3 15:48
 */
@Configuration
public class ThreadPoolParamConfig {
    /**
     * 核心线程池大小
     */
    @Value("${thread-pool.core-pool-size}")
    private int corePoolSize;

    /**
     * 最大线程数大小
     */
    @Value("${thread-pool.maximum-pool-size}")
    private int maximumPoolSize;

    /**
     * 活跃时间：秒
     */
    @Value("${thread-pool.keep-alive-seconds}")
    private int keepAliveSeconds;

    /**
     * 线程等待队列大小
     */
    @Value("${thread-pool.queue-capacity}")
    private int queueCapacity;

    /**
     * 自定义线程名称前缀
     */
    @Value("${thread-pool.thread-name-prefix}")
    private String threadNamePrefix;

    /**
     * 设置终止等待时间：秒
     */
    @Value("${thread-pool.await-termination-time}")
    private int awaitTerminationTime;

    /**
     * 线程结束前，是否等待线程队列中的任务执行完成
     */
    @Value("${thread-pool.wait-tasks-complete}")
    private boolean waitTasksComplete;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public int getAwaitTerminationTime() {
        return awaitTerminationTime;
    }

    public void setAwaitTerminationTime(int awaitTerminationTime) {
        this.awaitTerminationTime = awaitTerminationTime;
    }

    public boolean isWaitTasksComplete() {
        return waitTasksComplete;
    }

    public void setWaitTasksComplete(boolean waitTasksComplete) {
        this.waitTasksComplete = waitTasksComplete;
    }
}
