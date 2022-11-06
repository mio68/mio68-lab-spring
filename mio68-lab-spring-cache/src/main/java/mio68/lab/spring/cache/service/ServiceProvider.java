package mio68.lab.spring.cache.service;

import org.springframework.cache.annotation.Cacheable;

public interface ServiceProvider {
    String getById(int id);

    // Cache by projectKey and objectName
    @Cacheable(cacheManager = "serviceProviderCacheManager",
            cacheNames = "serviceProviderCache")
    String getByProjectKeyAndObjectName(String projectKey, String objectName);

    // Cache by projectKey and objectName
    @Cacheable(cacheManager = "serviceProviderCacheManager",
            cacheNames = "serviceProviderCache",
            key = "#contentId")
    String getContentHash(int contentId, String content);

}
