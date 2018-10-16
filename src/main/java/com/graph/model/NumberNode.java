package com.graph.model;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberNode)) return false;
        NumberNode that = (NumberNode) o;
        return Double.compare(that.data, data) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }


}
