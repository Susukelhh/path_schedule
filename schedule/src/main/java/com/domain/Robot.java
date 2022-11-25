package com.domain;

import lombok.Data;

@Data
public class Robot {
    private int id;
    private String origin;
    private String tasks;
    private double price;

    public Robot() {
    }

    public Robot(int id, String origin, String tasks, double price) {
        this.id = id;
        this.origin = origin;
        this.tasks = tasks;
        this.price = price;
    }
}
