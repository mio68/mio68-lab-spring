package mio68.lab.spring.cache.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ServiceProviderImpl implements ServiceProvider {
    private final ExternalService externalService;

    public ServiceProviderImpl(ExternalService externalService) {
        this.externalService = externalService;
    }

    // Cache by id key
    @Override
    @Cacheable(cacheManager = "serviceProviderCacheManager",
            cacheNames = "serviceProviderCache")
    public String getById(int id) {
        return externalService.getById(id);
    }


    // Cache by projectKey and objectName
    @Override
    @Cacheable(cacheManager = "serviceProviderCacheManager",
            cacheNames = "serviceProviderCache")
    public String getByProjectKeyAndObjectName(String projectKey, String objectName) {
        return externalService.getByProjectKeyAndObjectName(projectKey, objectName);
    }

    // Cache by contentId only
    @Override
    @Cacheable(cacheManager = "serviceProviderCacheManager",
            cacheNames = "serviceProviderCache",
            key = "#contentId")
    public String getContentHash(int contentId, String content) {
        return externalService.getContentHash(contentId, content);
    }

}
