package com.example.demo.dao.repositories;

import com.example.demo.dao.Visitors;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visitors}
 */
public interface IVisitorsRepository {

    Optional<Visitors> findVisitorByPhoneNumber(String phoneNumber);

    Optional<String> findTelephoneNumByChatId(Long chatId);

    Optional<Visitors> findVisitorByChatId(Long chatId);

    Optional<Long> findChatIdByPhoneNumber(String phoneNumber);

    boolean create(Visitors visitor);

    boolean updateByPhoneNumber(Visitors updateVisitors) throws Exception;

    boolean deleteVisitor(String phoneNumber);
}
