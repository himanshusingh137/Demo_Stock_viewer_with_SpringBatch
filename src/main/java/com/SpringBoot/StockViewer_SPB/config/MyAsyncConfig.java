//package com.SpringBoot.StockViewer_SPB.config;
//
//import java.util.concurrent.Executor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//
//@Configuration
//@EnableAsync(proxyTargetClass = true) 
//public class MyAsyncConfig {
//
//    @Bean(name ="myTaskExecutor")
//    public Executor myTaskExecutor(){
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(2);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("myThread-");
//        executor.initialize();
//        return executor;
//    }
//}
