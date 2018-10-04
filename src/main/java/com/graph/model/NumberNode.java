package com.graph.model;

public class NumberNode implements Node {

    private double data;

    public NumberNode(double data) {
        this.data = data;
    }

    @Override
    public double calculateValue() {
        return data;
    }

    @Override
    public String toString() {
        return "NumberNode{" +
                "data=" + data +
                '}';
    }
}
