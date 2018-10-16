package com.graph.model;

import com.graph.executor.SquareExecutorService;
import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.ExpressionCalculationException;
import com.graph.exception.ParseException;
import com.graph.traverser.ExpressionTreeTraverser;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Square implements Runnable {

    private String name;
    private Status status;
    private Double value;
    private String expression;
    private Node expressionTree;
    private Set<Square> observersGraph;
    private Set<Square> dependencyGraph;

    public Square(String name, Status status) {
        this.status = status;
        this.name = name;
        this.observersGraph = new HashSet<>();
        this.dependencyGraph = new HashSet<>();
    }

    @Override
    public void run() {
        if (allFieldsInitialized()) {
            calculateValue();
        }
    }

    public void clearDependencies() {
        for (Square square : this.dependencyGraph) {
            square.clearObserver(this);
        }
        this.dependencyGraph.clear();
    }

    public void initializeSquare(String expression, Node expressionTree) throws InterruptedException, ExecutionException {
        this.expression = expression;
        this.expressionTree = expressionTree;
        Double oldValue = this.value;
        if (allFieldsInitialized()) {
            calculateValue();
            if ((oldValue == null || !oldValue.equals(this.value)) && !this.observersGraph.isEmpty()) {
                Map<Integer, HashSet<Square>> calculationOrderMap = constructCalculationOrderMapForSquare(this);
                recalculateObserversExpressionTree(calculationOrderMap);
            }
        }
    }

    public void addObserver(Square observer) {
        observersGraph.add(observer);
    }

    public void addDependency(Square dependency) {
        dependencyGraph.add(dependency);
    }

    public void checkForCircularDependencies(Square square) throws CircularDependenciesException {
        if (square.equals(this)) {
            throw new CircularDependenciesException();
        }
        for (Square sq : this.observersGraph) {
            if (sq.equals(square)) {
                throw new CircularDependenciesException();
            }
            sq.checkForCircularDependencies(square);
        }
    }

    private void clearObserver(Square square) {
        this.observersGraph.remove(square);
    }

    private void calculateValue() {
        try {
            ExpressionTreeTraverser.calculateSquareValue(this);
            this.status = Status.INITIALIZED;
        } catch (CellNotInitializedException e) {
            this.status = Status.NOT_INITIALIZED;
        } catch (ParseException | ExpressionCalculationException e) {
            this.status = Status.ERROR;
        }
    }

    private void recalculateObserversExpressionTree(Map<Integer, HashSet<Square>> calculationOrderMap) throws InterruptedException, ExecutionException {
//        ExecutorService executor = SquareExecutorService.getExecutor();
//        for (Map.Entry<Integer, HashSet<Square>> entry : calculationOrderMap.entrySet()) {
//            List<Future> futures = new ArrayList<>();
//
//            for (Square square : entry.getValue()) {
//                Future future = executor.submit(square);
//                futures.add(future);
//            }
//            for (Future future : futures) {
//                future.get();
//            }
//        }
        for (Map.Entry<Integer, HashSet<Square>> entry : calculationOrderMap.entrySet()){
            for (Square square : entry.getValue()) {
                square.run();
            }
        }
    }

    private void addToCalculationOrderMap(Map<Integer, HashSet<Square>> calculationOrderMap, Set<Square> analyzedSquares, int level) {
        if (level > 0) {
            if (!calculationOrderMap.containsKey(level)) {
                calculationOrderMap.put(level, new HashSet<>());
            }
            if (analyzedSquares.contains(this)) {
                deleteExistingEntry(calculationOrderMap);
            }
            analyzedSquares.add(this);
            calculationOrderMap.get(level).add(this);
        }
        for (Square square : observersGraph) {
            square.addToCalculationOrderMap(calculationOrderMap, analyzedSquares, ++level);
        }
    }

    private static Map<Integer, HashSet<Square>> constructCalculationOrderMapForSquare(Square square) {
        Map<Integer, HashSet<Square>> calculationOrderMap = new HashMap<>();
        Map<Square, Integer> squareLevelMap = constructSquareLevelMap(square.getObserversGraph(), new HashMap<>(), 1);

        squareLevelMap.entrySet().forEach(entry -> {
            Integer level = entry.getValue();
            calculationOrderMap.computeIfAbsent(level, k -> new HashSet<>());
            calculationOrderMap.get(level).add(entry.getKey());
        });

        return calculationOrderMap;
    }

    private static Map<Square, Integer> constructSquareLevelMap(Set<Square> remainingObservers, Map<Square, Integer> analyzedSquares, int level) {
        if (remainingObservers.isEmpty()) {
            return analyzedSquares;
        }
        Set<Square> newObserverSet = new HashSet<>();
        int finalLevel = level;

        remainingObservers.forEach(observer -> {
            newObserverSet.addAll(observer.getObserversGraph());
            analyzedSquares.put(observer, finalLevel);
        });
        return constructSquareLevelMap(newObserverSet, analyzedSquares, ++level);
    }

    private void deleteExistingEntry(Map<Integer, HashSet<Square>> calculationOrderMap) {
        for (Set<Square> squareSet : calculationOrderMap.values()) {
            if (squareSet.contains(this)) {
                squareSet.remove(this);
                return;
            }
        }
    }

    private boolean allFieldsInitialized() {
        for (Square square : dependencyGraph) {
            if (square.getStatus() != Status.INITIALIZED) {
                return false;
            }
        }
        return true;
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

    String getExpression() {
        return expression;
    }

    public Node getExpressionTree() {
        return expressionTree;
    }

    public Set<Square> getObserversGraph() {
        return observersGraph;
    }

    public void setValue(Double value) {
        this.value = value;
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

    public enum Status {
        INITIALIZED,
        NOT_INITIALIZED,
        ERROR
    }
}
