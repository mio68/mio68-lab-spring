package mio68.lab.spring.web.mtls.client;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class ReactiveClient {

    public static void main(String[] args)
            throws KeyStoreException,
            NoSuchAlgorithmException,
            IOException,
            CertificateException,
            UnrecoverableKeyException {

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(
                new FileInputStream("certs/client/client.jks"),
                "changeit".toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, "changeit".toCharArray());

        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(
                new FileInputStream("certs/client/truststore.jks"),
                "changeit".toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        SslContext sslContext = SslContextBuilder.forClient()
                .keyManager(keyManagerFactory)
                .trustManager(trustManagerFactory)
                .build();

        HttpClient httpClient = HttpClient
                .create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        WebClient client = WebClient.builder()
                .baseUrl("https://localhost:8443/api/v1/hello")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        String response = client.get().retrieve().bodyToMono(String.class).block();
        System.out.println(response);
    }

}
