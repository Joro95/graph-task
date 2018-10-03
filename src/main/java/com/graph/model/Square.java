package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ExpressionCalculationException;
import com.graph.exception.ParseException;

import java.util.Objects;

public class Square {

    private String name;
    private Status status;
    private Double value;
    private String expression;
    private Node expressionTree;

    public Square(String name, Status status){
        this.status = status;
        this.name = name;
    }

    public void initializeSquare(String expression, Node expressionTree){
        this.expression = expression;
        this.expressionTree = expressionTree;
        calculateValue();
        this.status = Status.INITIALIZED;
    }

    void calculateValue(){
        try {
            this.value = expressionTree.calculateValue();
        } catch (ParseException | ExpressionCalculationException e) {
            this.status = Status.ERROR;
            return;
        } catch (CellNotInitializedException e) {
            this.status = Status.NOT_INITIALIZED;
            return;
        }
    }

    public enum Status{
        INITIALIZED,
        NOT_INITIALIZED,
        ERROR
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public double getValue() {
        return value;
    }

    public String getExpression() { return expression; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(name, square.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
