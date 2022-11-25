package com.domain;

import lombok.Data;

@Data
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
    private int robot_id;


    public Task() {
    }

    public Task(int id, String origin, String destination, int weight, int emergency, int species, int next_task, double value, double empty_price, double carry_price, int robot_id) {
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
        this.robot_id = robot_id;
    }
}
