package repositories;

import enteties.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository для работы с сущностью {@link enteties.Pass}
 */
@Repository
public interface PassRepository extends JpaRepository<Pass, Integer>{
}
