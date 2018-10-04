package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ParseException;

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
        try {
            this.value = expressionTree.calculateValue();
        } catch (ParseException e) {
            this.status = Status.ERROR;
            return;
        } catch (CellNotInitializedException e) {
            this.status = Status.NOT_INITIALIZED;
            return;
        }
        this.status = Status.INITIALIZED;
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
    public String toString() {
        return "Square{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", value=" + value +
                ", expression='" + expression + '\'' +
                ", expressionTree=" + expressionTree +
                '}';
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
