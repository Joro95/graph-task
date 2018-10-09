package com.graph.model;

import com.graph.SquareExecutorService;
import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.ParseException;

import java.util.*;
import java.util.concurrent.*;

public class Square implements Runnable, Callable{

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
        try {
            this.value = expressionTree.calculateValue();
            this.status = Status.INITIALIZED;
        } catch (CellNotInitializedException e) {
            this.status = Status.NOT_INITIALIZED;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void initializeSquare(String expression, Node expressionTree) throws InterruptedException {
        this.expression = expression;
        this.expressionTree = expressionTree;
        Map<Integer, HashSet<Square>> calculationOrderMap = new HashMap<>();
        addToCalculationOrderMap(calculationOrderMap, new HashSet<>(), 0);
        updateObservers(calculationOrderMap);
    }

    private void updateObservers(Map<Integer, HashSet<Square>> calculationOrderMap) throws InterruptedException {
        //iterate over map
        //executor run() on each square
        //check if all square updates are completed before going to the next layer
        ExecutorService executor = SquareExecutorService.getExecutor();
        for (Map.Entry<Integer, HashSet<Square>> entry : calculationOrderMap.entrySet()) {
            Collection<Callable<Square>> tasksToBeCompleted = new ArrayList<>();
            for (Square square : entry.getValue()) {
                tasksToBeCompleted.add(square);
                executor.execute(square);
            }
            executor.invokeAll(tasksToBeCompleted);
        }
    }

    void addToCalculationOrderMap(Map<Integer, HashSet<Square>> calculationOrderMap, Set<Square> analyzedSquares, int level){
        if (!calculationOrderMap.containsKey(level)){
            calculationOrderMap.put(level, new HashSet<>());
        }
        if (analyzedSquares.contains(this)){
            deleteExistingEntry(calculationOrderMap);
        }
        analyzedSquares.add(this);
        calculationOrderMap.get(level).add(this);
    }

    private void deleteExistingEntry(Map<Integer, HashSet<Square>> calculationOrderMap) {
        for (Set<Square> squareSet : calculationOrderMap.values()){
            if (squareSet.contains(this)){
                squareSet.remove(this);
                return;
            }
        }
    }

//    void calculateValue(Set<Square> updatedSquares){
//        for (Square square : dependencyGraph){
//            if (square.getStatus() != Status.INITIALIZED){
//                return;
//            }
//        }
//        if (!updatedSquares.contains(this)) {
//            try {
//                Double oldValue = this.value;
//                this.value = expressionTree.calculateValue();
//                this.status = Status.INITIALIZED;
//                if ((oldValue == null || !oldValue.equals(this.value)) && !this.observersGraph.isEmpty()) {
//                    recalculateObservers(updatedSquares);
//                }
//            } catch (ParseException | ExpressionCalculationException e) {
//                this.status = Status.ERROR;
//            } catch (CellNotInitializedException e) {
//                this.status = Status.NOT_INITIALIZED;
//            }
//        }
//        updatedSquares.add(this);
//    }

    void recalculateObservers(Set<Square> updatedSquares){
        for (Square square : observersGraph){
//            square.addToCalculationOrderMap();
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

    @Override
    public Object call() throws Exception {
        return this;
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
