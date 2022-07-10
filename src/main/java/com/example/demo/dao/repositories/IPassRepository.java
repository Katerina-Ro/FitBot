package com.example.demo.dao.repositories;

import com.example.demo.dao.Pass;
import lombok.NonNull;
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

    boolean updateIfFreeze(Pass dataFreeze);

    boolean updateIfUnFreeze(Integer passId);

    boolean updatePhoneNumberInPass(@NonNull String oldValuePhoneNumber, @NonNull String newValuePhoneNumber);

    boolean deletePass(Integer passId);

    boolean deletePass(String phoneNumber);
}