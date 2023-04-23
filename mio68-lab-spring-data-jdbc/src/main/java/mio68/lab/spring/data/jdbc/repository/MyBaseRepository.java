package mio68.lab.spring.data.jdbc.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/*
 * MyBaseRepository only contains two methods.
 * It won't be instantiated, because of @NoRepositoryBean.
 */
@NoRepositoryBean
interface MyBaseRepository<T, ID> extends Repository<T, ID> {

    Optional<T> findById(ID id);

    <S extends T> S save(S entity);

}