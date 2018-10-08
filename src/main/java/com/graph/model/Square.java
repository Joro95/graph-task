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
    private Set<Square> observersGraph;
    private Set<Square> dependencyGraph;

    public Square(String name, Status status){
        this.status = status;
        this.name = name;
        this.observersGraph = new HashSet<>();
        this.dependencyGraph = new HashSet<>();
    }

    public void initializeSquare(String expression, Node expressionTree){
        this.expression = expression;
        this.expressionTree = expressionTree;
        calculateValue(new HashSet<>());
    }

    void calculateValue(Set<Square> updatedSquares){
        for (Square square : dependencyGraph){
            if (square.getStatus() != Status.INITIALIZED){
                return;
            }
        }
        if (!updatedSquares.contains(this)) {
            try {
                Double oldValue = this.value;
                this.value = expressionTree.calculateValue();
                this.status = Status.INITIALIZED;
                if ((oldValue == null || !oldValue.equals(this.value)) && !this.observersGraph.isEmpty()) {
                    recalculateObservers(updatedSquares);
                }
            } catch (ParseException | ExpressionCalculationException e) {
                this.status = Status.ERROR;
            } catch (CellNotInitializedException e) {
                this.status = Status.NOT_INITIALIZED;
            }
        }
        updatedSquares.add(this);
    }

    void recalculateObservers(Set<Square> updatedSquares){
        for (Square square : observersGraph){
            square.calculateValue(updatedSquares);
        }
    }

    public void addObserver(Square observer){
        observersGraph.add(observer);
    }

    public void addDependency(Square dependency){
        dependencyGraph.add(dependency);
    }

    public void checkForCircularDependencies(Square square) throws CircularDependenciesException {
        if(square.equals(this)){
            throw new CircularDependenciesException();
        }
        for (Square sq : this.observersGraph){
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

    public void setValue(Double value) {
        this.value = value;
    }
}
