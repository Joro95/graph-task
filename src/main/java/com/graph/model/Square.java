package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.ExpressionCalculationException;
import com.graph.exception.ParseException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Square {

    private String name;
    private Status status;
    private Double value;
    private String expression;
    private Node expressionTree;
    private Set<Square> dependencyGraph;

    public Square(String name, Status status){
        this.status = status;
        this.name = name;
        this.dependencyGraph = new HashSet<>();
    }

    public void initializeSquare(String expression, Node expressionTree){
        this.expression = expression;
        this.expressionTree = expressionTree;
        calculateValue();
    }

    private void calculateValue(){
        try {
            Double oldValue = this.value;
            this.value = expressionTree.calculateValue();
            this.status = Status.INITIALIZED;
            if ((oldValue == null || !oldValue.equals(this.value)) && !this.dependencyGraph.isEmpty()) {
                recalculateDependencies();
            }
        } catch (ParseException | ExpressionCalculationException e) {
            this.status = Status.ERROR;
        } catch (CellNotInitializedException e) {
            this.status = Status.NOT_INITIALIZED;
        }
    }

    private void recalculateDependencies(){
        for (Square square : dependencyGraph){
            square.calculateValue();
        }
    }

    public void addDependency(Square dependency){
        dependencyGraph.add(dependency);
    }

    public void checkForCircularDependencies(Square square) throws CircularDependenciesException {
        for (Square sq : this.dependencyGraph){
            if (sq.equals(square)){
                throw new CircularDependenciesException();
            }
            sq.checkForCircularDependencies(square);
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

    String getExpression() { return expression; }

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
