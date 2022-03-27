package db.repositories;

import db.enteties.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visitors }
 */
@Repository
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {

    @Query("SELECT v From Visitors v WHERE v.visitors.telephoneNum = :telephoneNum")
    Optional<Visitors> findVisitorByPhoneNumber(@Param("telephoneNum") String phoneNumber);


    @Query("SELECT v.telephoneNum From Visitors v WHERE v.visitors.telephoneNum = :telephoneNum")
    String findTelephoneNum(@Param("telephoneNum")String phoneNumber);
}
