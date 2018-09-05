

package com.zbensoft.mmsmp.ownbiz.ra.own.task;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskScheduler {
    static final Logger logger = Logger.getLogger(TaskScheduler.class);
    static ExecutorService execPools = null;
    List<Tasker> tasks = new ArrayList();

    public TaskScheduler() {
    }

    public void startup() {
        if (execPools == null) {
            execPools = Executors.newFixedThreadPool(this.tasks.size() + 1);
            Iterator var2 = this.tasks.iterator();

            while(var2.hasNext()) {
                Tasker task = (Tasker)var2.next();
                execPools.execute(task);
            }

            logger.info("task scheduler startup with " + this.tasks.size() + " tasker");
        } else if (execPools != null) {
            logger.info("task scheduler is already startup");
        }

    }

    public void shutdown() {
        if (execPools != null) {
            execPools.shutdown();
            execPools = null;
        }

    }

    public void setTasks(List<Tasker> tasks) {
        this.tasks = tasks;
    }
}
