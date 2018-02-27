package com.baoli.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/*******************************************
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 17:36 2018\2\27 0027        
 *******************************************/

@Configuration
public class TaskExecutorConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskScheduler = new ThreadPoolTaskExecutor();
        taskScheduler.setCorePoolSize(5);
        taskScheduler.setMaxPoolSize(10);
        return taskScheduler;
    }
}
