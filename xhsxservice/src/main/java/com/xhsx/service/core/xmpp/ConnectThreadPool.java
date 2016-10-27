package com.xhsx.service.core.xmpp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 作者：lizy on 2016/10/13 16:11
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class ConnectThreadPool {
    private ExecutorService executorService;
    public int count;
    public ConnectThreadPool(){
        executorService = Executors.newSingleThreadExecutor();
    }
    public void shutDown(){ //关闭线程池
        executorService.shutdown();
    }
    //提交一个新的任务
    public Future submit(Runnable task) {
        Future result = null;
        if (!executorService.isTerminated()
                && !executorService.isShutdown()
                && task != null) {
            result = executorService.submit(task);
        }
        return result;
    }


}
