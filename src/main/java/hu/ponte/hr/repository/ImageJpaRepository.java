package hu.ponte.hr.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageJpaRepository extends CrudRepository<ImageJpaEntity, Long> {
}
