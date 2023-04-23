package mio68.lab.spring.data.jdbc.repository;

import mio68.lab.spring.data.jdbc.entity.Resource;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MyRepository2 extends Repository<Resource, Long> {

    Optional<Resource> findById(Long id);

    Resource save(Resource resource);

}