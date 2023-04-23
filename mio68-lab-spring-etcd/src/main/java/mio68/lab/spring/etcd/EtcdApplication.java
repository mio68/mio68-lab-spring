package mio68.lab.spring.etcd;

import com.google.protobuf.ByteString;
import com.ibm.etcd.api.RangeResponse;
import com.ibm.etcd.client.KvStoreClient;
import com.ibm.etcd.client.kv.KvClient;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
@Slf4j
public class EtcdApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtcdApplication.class, args);
    }

    @Component
    static class EtcdApplicationDemo implements ApplicationRunner {

        private final Client etcdClient;
        private final KvStoreClient kvStoreClient;

        EtcdApplicationDemo(Client etcdClient, KvStoreClient kvStoreClient) {
            this.etcdClient = etcdClient;
            this.kvStoreClient = kvStoreClient;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            try {
                jetcdDemo();

                System.out.println("Press <enter> key for another try");
                System.in.read();
                jetcdDemo();

//                etcdJavaDemo();
                close();
            } catch (Exception e) {
                log.error("error",e);
            }
        }

        private void etcdJavaDemo() {
            KvClient kvClient = kvStoreClient.getKvClient();
            RangeResponse response = kvClient.get(ByteString.copyFromUtf8("testkey")).sync();
            response.getKvsList().forEach(keyValue -> log.info(keyValue.toString()));
        }


        private void jetcdDemo() {
            KV kvClient = etcdClient.getKVClient();
            ByteSequence key = ByteSequence.from("testkey".getBytes());
            CompletableFuture<GetResponse> getResponseCompletableFuture
                    = kvClient.get(key);

            try {
                log.info("get key-value from etcd");
                GetResponse getResponse = getResponseCompletableFuture.get();
                log.info("getResponse.getKvs() {}" , getResponse.getKvs());
                getResponse.getKvs().forEach(keyValue ->
                        log.info(
                                "value: [{}]",
                                new String(keyValue.getValue().getBytes())));
            } catch (Exception e) {
                log.error("can't get value from etcd", e);
            }

            kvClient.close();
        }

        private void close() throws IOException {
            etcdClient.close();
            kvStoreClient.close();
        }
    }

}
