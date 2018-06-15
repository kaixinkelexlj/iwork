package com.work.job.thread;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.util.NamedThreadFactory;
import org.slf4j.LoggerFactory;

/**
 * @author xulujun
 * @date 2017/12/18
 */
public class MonitorableThreadPoolExecutor extends ThreadPoolExecutor {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MonitorableThreadPoolExecutor.class);

    private ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

    private String poolName;
    private ThreadPoolConfig threadPoolConfig;

    public MonitorableThreadPoolExecutor(String poolName,
                                         int corePoolSize,
                                         int maximumPoolSize,
                                         long keepAliveTime, TimeUnit unit,
                                         BlockingQueue<Runnable> workQueue,
                                         RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new NamedThreadFactory(poolName), new LogRejectedPolicy(poolName, rejectedExecutionHandler));
        this.poolName = poolName;
        monitorStart();
    }

    @Override
    public void shutdown() {
        shutdownQuietly(timer);
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        shutdownQuietly(timer);
        return super.shutdownNow();
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public ThreadPoolConfig getThreadPoolConfig() {
        return threadPoolConfig;
    }

    public void setThreadPoolConfig(ThreadPoolConfig threadPoolConfig) {
        this.threadPoolConfig = threadPoolConfig;
    }

    private void monitorStart() {
        timer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int activeSize = MonitorableThreadPoolExecutor.this.getActiveCount();
                int maxSize = MonitorableThreadPoolExecutor.this.getMaximumPoolSize();
                boolean warn = (activeSize / (maxSize * 1.0d)) > 0.8;
                logToMonitor(
                    MonitorableThreadPoolExecutor.this.getPoolName(),
                    MonitorableThreadPoolExecutor.this.getCorePoolSize(),
                    maxSize,
                    MonitorableThreadPoolExecutor.this.getLargestPoolSize(),
                    activeSize,
                    MonitorableThreadPoolExecutor.this.getPoolSize(),
                    MonitorableThreadPoolExecutor.this.getQueue().size(),
                    warn,
                    warn ? "pool-size-warning" : "ok"
                );
                if (threadPoolConfig != null) {
                    int maxSizeNew = threadPoolConfig.getMaximumPoolSize(poolName);
                    if (maxSizeNew >= MonitorableThreadPoolExecutor.this.getCorePoolSize() && maxSizeNew != maxSize) {
                        MonitorableThreadPoolExecutor.this.setMaximumPoolSize(maxSizeNew);
                        logToMonitor(
                            MonitorableThreadPoolExecutor.this.getPoolName(),
                            MonitorableThreadPoolExecutor.this.getCorePoolSize(),
                            maxSize,
                            MonitorableThreadPoolExecutor.this.getLargestPoolSize(),
                            activeSize,
                            MonitorableThreadPoolExecutor.this.getPoolSize(),
                            MonitorableThreadPoolExecutor.this.getQueue().size(),
                            false,
                            "resize[" + maxSizeNew + "]"
                        );
                    }
                }
            }
        }, 1, 10, TimeUnit.SECONDS);
    }

    private static void shutdownQuietly(ExecutorService pool) {
        try {
            pool.shutdownNow();
        } catch (Exception ex) {

        }
    }

    public static class LogRejectedPolicy implements RejectedExecutionHandler {

        private String poolName;
        private RejectedExecutionHandler rejectedExecutionHandler;

        public LogRejectedPolicy(String poolName, RejectedExecutionHandler rejectedExecutionHandler) {
            this.poolName = poolName;
            this.rejectedExecutionHandler = rejectedExecutionHandler == null ? new AbortPolicy() : rejectedExecutionHandler;
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            logToMonitor(
                poolName,
                e.getCorePoolSize(),
                e.getMaximumPoolSize(),
                e.getLargestPoolSize(),
                e.getActiveCount(),
                e.getPoolSize(),
                e.getQueue().size(),
                true,
                "reject@" + rejectedExecutionHandler.getClass().getSimpleName()
            );
            this.rejectedExecutionHandler.rejectedExecution(r, e);
        }

    }

    private static void logToMonitor(String poolName, int corePoolSize, int maximumPoolSize, int largestPoolSize, int activeSize, int currentPoolSize, int queuedTaskSize, boolean isWarn, String reason) {
        if (isWarn) {
            LOGGER.error(
                "pool[{}], corePoolSize[{}], maximumPoolSize[{}], largestPoolSize[{}], activeSize[{}], currentPoolSize[{}], queuedTaskSize[{}], status[{}], reason[{}]",
                poolName,
                corePoolSize,
                maximumPoolSize,
                largestPoolSize,
                activeSize,
                currentPoolSize,
                queuedTaskSize,
                isWarn ? "warning" : "ok",
                reason
            );
        } else {
            LOGGER.info(
                "pool[{}], corePoolSize[{}], maximumPoolSize[{}], largestPoolSize[{}], activeSize[{}], currentPoolSize[{}], queuedTaskSize[{}], status[{}], reason[{}]",
                poolName,
                corePoolSize,
                maximumPoolSize,
                largestPoolSize,
                activeSize,
                currentPoolSize,
                queuedTaskSize,
                isWarn ? "warning" : "ok",
                reason
            );
        }
    }

    /**
     * @author xulujun
     * @date 2017/12/20
     */
    public static interface ThreadPoolConfig {

        /**
         * 根据poolName获取maximumPoolSize
         *
         * @param poolName
         * @return
         */
        int getMaximumPoolSize(String poolName);

    }

}
