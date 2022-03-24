package repositories;

import enteties.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository для работы с сущностью {@link enteties.Visits}
 */
public interface VisitsRepository extends JpaRepository<Visits, Integer> {
}
