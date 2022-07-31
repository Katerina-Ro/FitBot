package com.example.demo.dao.repositories.support;

import com.example.demo.dao.supportTables.ComeToDay;

import java.util.List;
import java.util.Optional;

public interface IComeToDay {
    Optional<List<ComeToDay>> getAllComeToDay();

    boolean deleteComeToDay(String phoneNumber);
}
