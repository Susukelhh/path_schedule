package com.domain;

import lombok.Data;

@Data
public class Schedule {
    private int id;
    private String firstTasks;
    private String secondTasks;
    private double firstPrice;
    private double secondPrice;
    private double allPrice;
}
