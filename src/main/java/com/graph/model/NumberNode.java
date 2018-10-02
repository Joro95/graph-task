package com.graph.model;

public class NumberNode extends Node {

    private double data;

    public NumberNode(double data) {
        this.data = data;
    }

    protected double calculateValue() {
        return data;
    }
}
