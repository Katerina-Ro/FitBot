package com.example.demo.dao.key;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode
@ToString
public class VisitsKey implements Serializable {
    static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private Integer pass;

    @Getter
    @Setter
    private LocalDate dateVisit;
}
