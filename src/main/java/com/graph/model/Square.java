package com.graph.model;

import com.graph.ThreadPoolSquareExecutor;
import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.ExpressionCalculationException;
import com.graph.exception.ParseException;

import java.util.*;
import java.util.concurrent.*;

public class Square implements Runnable{

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

    @Override
    public void run() {
        if(allFieldsInitialized()) {
            calculateValue();
        }
    }

    public void initializeSquare(String expression, Node expressionTree) throws InterruptedException, ExecutionException {
        long x = System.currentTimeMillis();
        this.expression = expression;
        this.expressionTree = expressionTree;
        Double oldValue = this.value;
        if (allFieldsInitialized()) {
            calculateValue();
            if ((oldValue == null || !oldValue.equals(this.value)) && !this.observersGraph.isEmpty()) {
                Map<Integer, HashSet<Square>> calculationOrderMap = new HashMap<>();
                addToCalculationOrderMap(calculationOrderMap, new HashSet<>(), 0);
                recalculateObserversExpressionTree(calculationOrderMap);
            }
        }
        System.out.println(System.currentTimeMillis() - x);
    }

    private boolean allFieldsInitialized() {
        for(Square square : dependencyGraph){
            if (square.getStatus() != Status.INITIALIZED){
                return false;
            }
        }
        return true;
    }

    private void calculateValue(){
        try {
            this.value = expressionTree.calculateValue();
            this.status = Status.INITIALIZED;
        } catch (CellNotInitializedException e) {
            this.status = Status.NOT_INITIALIZED;
        } catch (ParseException | ExpressionCalculationException e) {
            this.status = Status.ERROR;
        }
    }

    private void recalculateObserversExpressionTree(Map<Integer, HashSet<Square>> calculationOrderMap) throws InterruptedException, ExecutionException {
        ExecutorService executor = ThreadPoolSquareExecutor.getExecutor();
        for (Map.Entry<Integer, HashSet<Square>> entry : calculationOrderMap.entrySet()) {
            List<Future> futures = new ArrayList<>();
            Collection<Runnable> tasksToBeCompleted = new ArrayList<>();
            for (Square square : entry.getValue()) {
                tasksToBeCompleted.add(square);
                Future future = executor.submit(square);
                futures.add(future);
            }
            for (Future future : futures){
                future.get();
            }
        }
    }

    void addToCalculationOrderMap(Map<Integer, HashSet<Square>> calculationOrderMap, Set<Square> analyzedSquares, int level){
        if(level > 0){
            if (!calculationOrderMap.containsKey(level)){
                calculationOrderMap.put(level, new HashSet<>());
            }
            if (analyzedSquares.contains(this)){
                deleteExistingEntry(calculationOrderMap);
            }
            analyzedSquares.add(this);
            calculationOrderMap.get(level).add(this);
        }
        for (Square square : observersGraph){
            square.addToCalculationOrderMap(calculationOrderMap, analyzedSquares, ++level);
        }
    }

    private void deleteExistingEntry(Map<Integer, HashSet<Square>> calculationOrderMap) {
        for (Set<Square> squareSet : calculationOrderMap.values()){
            if (squareSet.contains(this)){
                squareSet.remove(this);
                return;
            }
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

    public String getExpression() {
        return expression;
    }

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
