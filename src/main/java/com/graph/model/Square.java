package com.graph.model;

public class Square {

    private String name;
    private Status status;
    private int value;
    private String expression;
    private Node expressionTree;

    public Square(Status status) {
        this.status = status;
    }

    public Square(String name, Status status){
        this(status);
        this.name = name;
    }

    public Square(String name, String expression, Status status){
        this(name, status);
        this.expression = expression;
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

    public void setStatus(Status status) {
        this.status = status;
    }
}
