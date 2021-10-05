package hu.ponte.hr.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageMetaJpaRepository extends CrudRepository<ImageMetaJpaEntity, Long> {

    List<ImageMetaJpaEntity> findAll();
}
