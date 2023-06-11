package mio68.lab.spring.web.mtls.client;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Client {

    public static void main(String[] args)
            throws IOException,
            CertificateException,
            NoSuchAlgorithmException,
            KeyStoreException,
            KeyManagementException,
            UnrecoverableKeyException {

        RestTemplate restTemplate = createRestTemplate();
        String result = restTemplate.getForObject(
                "https://localhost:8443/api/v1/hello",
                String.class);
        System.out.println(result);
    }

    private static RestTemplate createRestTemplate()
            throws NoSuchAlgorithmException,
            KeyManagementException,
            KeyStoreException,
            CertificateException,
            IOException,
            UnrecoverableKeyException {

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(
                        new File("certs/client/truststore.jks"),
                        "changeit".toCharArray())
                .loadKeyMaterial(
                        new File("certs/client/client.jks"),
                        "changeit".toCharArray(),
                        "changeit".toCharArray())
                .build();

        SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslContext);

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslConnectionSocketFactory)
//                .register("http", new PlainConnectionSocketFactory())
                .build();

        BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        final HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }

}
