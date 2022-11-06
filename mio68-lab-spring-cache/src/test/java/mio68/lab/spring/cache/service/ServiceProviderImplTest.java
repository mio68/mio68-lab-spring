package mio68.lab.spring.cache.service;

import mio68.lab.spring.cache.CacheConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ServiceProviderImplTest {

    @MockBean
    ExternalService externalService;

    @Autowired
    ServiceProviderImpl serviceProvider;

    @Value("${cache.expireAfterWriteSec}")
    int expireAfterWriteSec;

    @Test
    public void getById_whenCallTwice_thanSecondResultComesFromCache() {
        String s = "Hello!";
        when(externalService.getById(anyInt())).thenReturn(s);

        // First call
        String res = serviceProvider.getById(1);
        assertEquals(s, res);

        // Second call
        res = serviceProvider.getById(1);
        assertEquals(s, res);

        // Verify that external service called just once.
        verify(externalService, times(1)).getById(anyInt());
    }

    @Test
    public void getById_whenSecondCallAfterTimeout_thanSecondResultComesFromServiceToo() throws InterruptedException {
        String s = "Hello!";
        when(externalService.getById(anyInt())).thenReturn(s);

        // First call
        String res = serviceProvider.getById(1);
        assertEquals(s, res);

        // Wait for cache expiration
        TimeUnit.SECONDS.sleep(expireAfterWriteSec + 1);

        // Second call
        res = serviceProvider.getById(1);
        assertEquals(s, res);

        // Verify that external service called two times.
        verify(externalService, times(2)).getById(anyInt());
    }

    @Test
    public void getByProjectKeyAndName_whenCallTwice_thanSecondResultComesFromCache() {
        when(externalService.getByProjectKeyAndObjectName("prjA","obj1")).thenReturn("prjA.obj1");
        when(externalService.getByProjectKeyAndObjectName("prjA","obj2")).thenReturn("prjA.obj2");

        // First call for obj1
        String res = serviceProvider.getByProjectKeyAndObjectName("prjA", "obj1");
        assertEquals("prjA.obj1", res);
        // Second call for obj1
        res = serviceProvider.getByProjectKeyAndObjectName("prjA", "obj1");
        assertEquals("prjA.obj1", res);

        // Verify that external service called just once.
        verify(externalService, times(1))
                .getByProjectKeyAndObjectName("prjA", "obj1");

        // First call for obj2
        res = serviceProvider.getByProjectKeyAndObjectName("prjA", "obj2");
        assertEquals("prjA.obj2", res);
        // Second call for obj2
        res = serviceProvider.getByProjectKeyAndObjectName("prjA", "obj2");
        assertEquals("prjA.obj2", res);

        // Verify that external service called just once.
        verify(externalService, times(1))
                .getByProjectKeyAndObjectName("prjA", "obj2");

    }

    @Test
    public void getContentHash_whenCallWithId_whenReturnsSameHashByIdFromCash() {
        when(externalService.getContentHash(1,"content1")).thenReturn("hash.content1");
        when(externalService.getContentHash(1,"content2")).thenReturn("hash.content2");

        // It's cached by id only, not depends on content
        String res = serviceProvider.getContentHash(1, "content1");
        assertEquals("hash.content1", res);

        // got the same cash, because id is primary key of content
        res = serviceProvider.getContentHash(1, "content2");
        assertEquals("hash.content1", res);

        // Verify that external service called just once.
        verify(externalService, times(1))
                .getContentHash(1, "content1");

        verify(externalService, times(0))
                .getContentHash(1, "content2");

    }

}