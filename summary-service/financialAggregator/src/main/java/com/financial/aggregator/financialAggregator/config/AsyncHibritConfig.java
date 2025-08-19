package com.financial.aggregator.financialAggregator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncHibritConfig {
    /**
     * Hibrit Virtual Thread + Carrier Thread Control Yaklaşımı
     *
     * Bu konfigürasyon şunları sağlar:
     *
     * 1. VIRTUAL THREAD AVANTAJLARI:
     *    - Her task Virtual Thread'de çalışır (~1KB memory)
     *    - Milyonlarca virtual thread oluşturulabilir
     *    - I/O blocking'de carrier thread başka virtual thread'lere geçer
     *
     * 2. CARRIER THREAD KONTROLÜ (JVM Level):
     *    - jdk.virtualThreadScheduler.parallelism system property ile kontrol
     *    - jdk.virtualThreadScheduler.maxPoolSize ile max carrier thread sayısı
     *    - Production'da öngörülebilir carrier thread sayısı
     *
     * 3. SPRING ENTEGRASYONu:
     *    - @Async annotation'ları ile çalışır
     *    - Spring'in monitoring ve lifecycle yönetimi
     *
     * NOT: Carrier thread kontrolü için JVM başlatma parametreleri:
     * -Djdk.virtualThreadScheduler.parallelism=5
     * -Djdk.virtualThreadScheduler.maxPoolSize=10
     *
     * @return Virtual Thread Executor with Controlled Carriers
     */

    //TODO: burada şunlar jvm parametresi olarak verilmeli: -Djdk.virtualThreadScheduler.parallelism=5
    //-Djdk.virtualThreadScheduler.maxPoolSize=10
    @Bean(name = "taskHibritExecutor")
    public Executor taskExecutor() {
        // JVM seviyesinde carrier thread kontrolü - Test için azaltıldı
        //TODO: JVM ayarı olarak verdim.
//        System.setProperty("jdk.virtualThreadScheduler.parallelism", "2");
//        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "3");

        // Virtual Thread Factory ile her thread için unique isim
        var virtualThreadFactory = Thread.ofVirtual()
                .name("VirtualWorker-", 0)  // 0'dan başlayarak otomatik increment
                .factory();

        // Custom Executor - Virtual Thread + Carrier Thread Logging
        return task -> {
            virtualThreadFactory.newThread(() -> {
                task.run();
            }).start();
        };
    }


}
