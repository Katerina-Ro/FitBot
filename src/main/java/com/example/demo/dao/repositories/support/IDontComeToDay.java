package com.example.demo.dao.repositories.support;

import com.example.demo.dao.supportTables.DontComeToDay;

import java.util.List;
import java.util.Optional;

public interface IDontComeToDay {
    Optional<List<DontComeToDay>> getDontComeToDay();
}
