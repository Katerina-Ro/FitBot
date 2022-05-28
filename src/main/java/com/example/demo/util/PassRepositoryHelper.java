package com.example.demo.util;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.repositories.IPassRepository;
import com.example.demo.dao.repositories.impl.PassRepository;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;

public class PassRepositoryHelper {
    private final IPassRepository passRepository;

    public PassRepositoryHelper() {
        this.passRepository = new PassRepository(new DBConfig().namedParameterJdbcTemplate());
    }

    public ObservableList<Pass> getTablePass(StringProperty inputPhoneNumber){
        String phoneNumber = String.valueOf(inputPhoneNumber.get());
        return FXCollections.observableArrayList(getListPass(phoneNumber));
    }

    public List<Pass> getListPass(String phoneNumber){
        if (phoneNumber != null) {
            Optional<List<Pass>> listPass = passRepository.findPassByPhone(phoneNumber);
            boolean l = listPass.isEmpty();
            if (listPass.isPresent() && !listPass.get().isEmpty()) {
                if (listPass.get().size() == 1) {
                    return listPass.get();
                } else {
                    // здесь вписать новое окно с выбором, какой именно абонемент (из двух) нужен?
                    return FXCollections.emptyObservableList();
                }
            }
        }
        return FXCollections.emptyObservableList();
    }
}
