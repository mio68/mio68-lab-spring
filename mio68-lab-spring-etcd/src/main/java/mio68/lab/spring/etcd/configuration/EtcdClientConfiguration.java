package mio68.lab.spring.etcd.configuration;

import com.ibm.etcd.client.EtcdClient;
import com.ibm.etcd.client.KvStoreClient;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
//@EnableAsync
public class EtcdClientConfiguration {

    @Bean(destroyMethod = "close")
    public Client etcdClient(){
        ClientBuilder clientBuilder = Client.builder()
                .endpoints(
                        "http://localhost:2379",
                        "http://localhost:2380")
//                .executorService(executorService)
                .waitForReady(false)
//                .retryMaxDuration(Duration.ofSeconds(40))
                .retryDelay(3)
//                .retryMaxDelay(30)
                .retryChronoUnit(ChronoUnit.SECONDS)
                .connectTimeout(Duration.ofSeconds(2));

//                .retryChronoUnit(ChronoUnit.SECONDS)
//                .connectTimeout(Duration.ofSeconds(10));

        return  clientBuilder.build();
    }

//    @Bean(destroyMethod = "shutdownNow")
//    public ExecutorService executorService() {
//        return Executors.newFixedThreadPool(10);
//    }


    @Bean
    KvStoreClient kvStoreClient() {
        return EtcdClient.forEndpoint("localhost", 2379)
                .withPlainText()
                .build();
    }

}
