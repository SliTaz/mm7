

package com.zbensoft.mmsmp.ownbiz.ra.own.listener;

import com.zbensoft.mmsmp.ownbiz.ra.own.task.TaskScheduler;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

public class TaskListener implements ApplicationListener {
    private static final Logger _log = Logger.getLogger(TaskListener.class);
    private static final String CLASS_NAME = "TaskListener ";
    private TaskScheduler taskScheduler;

    public TaskListener() {
    }

    public void onApplicationEvent(ApplicationEvent event) {

        try {
            _log.info(String.format("%s监听器处理开始.....", "TaskListener "));
            if (event instanceof ContextRefreshedEvent) {
                _log.info(String.format("%s任务管理器启动.", "TaskListener "));
            } else if (event instanceof ContextClosedEvent) {
                _log.info(String.format("%s任务管理器停止.", "TaskListener "));
                this.taskScheduler.shutdown();
            }

            _log.info(String.format("%s监听器处理结束.", "TaskListener "));
        } catch (Exception var3) {
            _log.error(String.format("%s监听器处理失败，抛出异常: %s.", "TaskListener ", var3.getMessage()));
        }

    }

    public TaskScheduler getTaskScheduler() {
        return this.taskScheduler;
    }

    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }
}
