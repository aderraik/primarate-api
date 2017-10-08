package com.visiansystems;

import com.visiansystems.rates.RatesFetcherDaemon;
import com.visiansystems.util.MonetaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class WebAppContext {
    @Autowired
    private MonetaryUtils monetaryUtils;

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public CommandLineRunner schedulingRunner(TaskExecutor executor) {
        return args -> executor.execute(new RatesFetcherDaemon(monetaryUtils));
    }
}
