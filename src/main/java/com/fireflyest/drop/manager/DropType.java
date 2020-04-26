package com.fireflyest.drop.manager;

public enum DropType{
    NORMAL(0, 0.70),
    LONGER(1, 1.60),
    QUADRATE(2, 0.0),
    BONE(3, 1.07);

    public int id;
    public double height;

    DropType(int id, double height) {
        this.id = id;
        this.height = height;
    }
}
