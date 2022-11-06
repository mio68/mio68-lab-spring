package mio68.lab.spring.cache.service;

public interface ExternalService {

    String getById(int id);

    String getByProjectKeyAndObjectName(String projectKey, String objectName);

    String getContentHash(int contentId, String content);
}
