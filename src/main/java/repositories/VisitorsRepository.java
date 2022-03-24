package repositories;

import enteties.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository для работы с сущностью {@link enteties.Visitors}
 */
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {
}
