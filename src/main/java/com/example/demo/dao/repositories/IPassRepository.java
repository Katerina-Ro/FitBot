package com.example.demo.dao.repositories;

import com.example.demo.dao.Pass;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Pass }
 */
public interface IPassRepository {

    Optional<String> findPhoneNumberByPassId(Integer passId);

    Optional<List<Pass>> findPassByPhone(String phoneNumber);

    Optional<Pass> findPassByPassId(Integer passId);

    boolean createPass(Pass pass);

    boolean update(Pass updatedPass);

    boolean deletePass(Integer passId);

    boolean deletePass(String phoneNumber);
}


/*
public interface PassRepository extends JpaRepository<Pass, Integer> {

    @Query(value = "SELECT pass_schema.pass_table.phoneNumber FROM pass_schema.pass_table " +
            "WHERE pass_schema.pass_table.pass_id = :numPass", nativeQuery = true)
    String findPhoneNumberByPassId(@Param("numPass") Integer passId);

    /*@Query(value = "SELECT pass_schema.pass_table.pass_id, pass_schema.pass_table.tel_num, pass_schema.pass_table.date_start," +
            " pass_schema.pass_table.date_end, pass_schema.pass_table.visit_limit, pass_schema.pass_table.freeze_limit, " +
            "pass_schema.pass_table.date_freeze FROM pass_schema.pass_table WHERE pass_schema.pass_table.tel_num = :phoneNumber",
            nativeQuery = true)*/
    /*@Query(value = "SELECT pass_id, date_start, date_end, visit_limit, freeze_limit, date_freeze " +
            "FROM pass_schema.pass_table WHERE pass_schema.pass_table.tel_num = :phoneNumber",
            nativeQuery = true)
    @Query(value = "SELECT * FROM pass_schema.pass_table WHERE pass_schema.pass_table.tel_num = :phoneNumber",
            nativeQuery = true)
    //@Query("SELECT p FROM Pass p WHERE p.visitors.telephoneNum = :phoneNumber")
    Optional<List<Pass>> findPassByPhone(@Param("phoneNumber") String phoneNumber);

*/