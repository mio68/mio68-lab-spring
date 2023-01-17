package mio68.lab.spring.webflux.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.hamcrest.Matchers.startsWith;

@WebFluxTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenHelloWithMono_thenOk() {
        webTestClient.get()
                .uri("/api/v1/hello/mono")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .value(startsWith("Hello World"));
    }

    @Test
    public void whenHelloWithCompletableFuture_thenOk() {
        webTestClient.get()
                .uri("/api/v1/hello/completable")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .value(startsWith("Hello World"));
    }

}