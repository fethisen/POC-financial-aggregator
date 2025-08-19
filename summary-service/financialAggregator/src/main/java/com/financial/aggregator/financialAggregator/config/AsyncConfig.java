package com.financial.aggregator.financialAggregator.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * setCorePoolSize(5) : Thread havuzunda en az 5 thread hazır bulunur. Eğer bu sayının altında çalışacak iş varsa, yeni thread oluşturulmadan mevcutlar kullanılır.
     * setMaxPoolSize(10): Core (5) thread doluysa ve queue (iş kuyruğu) da dolmuşsa, yeni gelen iş için ek thread oluşturulabilir (10’a kadar).
     * setQueueCapacity(50) : Core thread sayısı dolduğunda, işlerin bekletildiği kuyruktur.Örneğin 5 thread çalışıyor ve yeni iş geldi, önce bu kuyruğa alınır (50 işlik kapasite var).
     *                          Kuyruk da dolarsa ve hâlâ iş gelirse, ek thread (core dışı) yaratılır (10’a kadar).
     * @return
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);   // eşzamanlı çalışan iş sayısı
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("Worker-");
        executor.initialize();
        return executor;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
