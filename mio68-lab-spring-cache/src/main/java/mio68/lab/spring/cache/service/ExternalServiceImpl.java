package mio68.lab.spring.cache.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalServiceImpl implements ExternalService {

    @Override
    public String getById(int id) {
        log.info("getById({})", id);
        return "Hello " + id + "!";
    }

    @Override
    public String getByProjectKeyAndObjectName(String projectKey, String objectName) {
        return projectKey + "." + objectName;
    }

    @Override
    public String getContentHash(int contentId, String content) {
        return "hash of content with id: " + contentId;
    }
}
