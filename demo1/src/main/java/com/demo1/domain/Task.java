package com.demo1.domain;

import lombok.Data;

public class Task {
    private int id;
    private String origin;
    private String destination;
    private int weight;
    private int emergency;
    private int species;
    private int next_task;
    private double value;
    private double empty_price;
    private double carry_price;

    public Task() {
    }

    public Task(int id, String origin, String destination, int weight, int emergency, int species, int next_task, double value, double empty_price, double carry_price) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.emergency = emergency;
        this.species = species;
        this.next_task = next_task;
        this.value = value;
        this.empty_price = empty_price;
        this.carry_price = carry_price;
    }
}
