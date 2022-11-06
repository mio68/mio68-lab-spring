package mio68.lab.spring.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
// Don't forget to add @EnableCaching annotation
@EnableCaching
public class CacheConfiguration {

    public static final String SERVICE_PROVIDER_CACHE_NAME = "serviceProviderCache";

    // Configure a cache manager
    @Bean
    public CacheManager serviceProviderCacheManager(
            @Value("${cache.expireAfterWriteSec:60}") int expireAfterWriteSec) {

        log.info("cache.expireAfterWriteSec [{}]", expireAfterWriteSec);
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().expireAfterWrite(expireAfterWriteSec, TimeUnit.SECONDS);
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(SERVICE_PROVIDER_CACHE_NAME);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
