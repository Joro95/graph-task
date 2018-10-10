package com.graph.benchmark;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.InvalidInputException;
import com.graph.exception.ParseException;
import com.graph.facade.GraphFacade;

import java.util.concurrent.ExecutionException;

/**
 * Created by developer on 10/10/18.
 */
public class TestDataGenerator {

    private TestDataGenerator() {
        throw new IllegalStateException();
    }

    public static void generateData(GraphFacade graphFacade, int sizeOfData) {
        //TODO
        //generate expression string
        //generate dependencies for current cell;
        //process expression from graphFacade
        try {
            graphFacade.processExpression("A1=B1+C1");
            graphFacade.processExpression("B1=C1");
        } catch (InvalidInputException | CircularDependenciesException | CellNotInitializedException | ParseException | InterruptedException | ExecutionException e) {
            e.getMessage();
        }
    }
}
