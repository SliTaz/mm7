package com.zbensoft.mmsmp.api.config.quartz;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.zbensoft.mmsmp.api.quartz.task.ScheduledTaskService")
@EnableScheduling
public class TaskConfig {

}
