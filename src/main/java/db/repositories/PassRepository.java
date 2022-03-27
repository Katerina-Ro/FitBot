package db.repositories;

import db.enteties.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository для работы с сущностью {@link Pass }
 */
//@Repository
public interface PassRepository extends JpaRepository<Pass, Integer>{

    @Query("SELECT p.chatId From Pass p WHERE p.numPass = :numPass")
    Long findChatIdByPassId(@Param("numPass") Integer passId);
}
