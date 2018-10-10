package com.graph.benchmark;

import com.graph.converter.Converter;
import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.InvalidInputException;
import com.graph.exception.ParseException;
import com.graph.facade.GraphFacade;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by developer on 10/10/18.
 */
public class TestDataGenerator {

    private TestDataGenerator() {
        throw new IllegalStateException();
    }

    public static String generateData(GraphFacade graphFacade, int sizeOfData) {
        String lastDependencyName = Converter.columnToLetters(sizeOfData - 1) + "1";
        try {
            StringBuilder expression = new StringBuilder();
            expression.append("A1=");
            for (int i = 0; i < sizeOfData - 2; i++) {
                String reference = Converter.columnToLetters(i + 1);
                expression.append(reference).append(1);
                StringBuilder dependencies = new StringBuilder();
                dependencies.append(reference).append(1).append("=");
                System.out.println(expression);
                for (int j = i + 1; j < sizeOfData - 1; j++) {
                    String dependencyReference = Converter.columnToLetters(j + 1);
                    dependencies.append(dependencyReference).append(1).append(generateRandomArithmeticOperation());
                }
                dependencies.append(1);
                graphFacade.processExpression(dependencies.toString());
                expression.append(generateRandomArithmeticOperation());
                System.out.println(dependencies);
            }
            expression.append(1);
            graphFacade.processExpression(expression.toString());
        } catch (InvalidInputException | CircularDependenciesException | CellNotInitializedException | ParseException | InterruptedException | ExecutionException e) {
            System.out.println("ERROR");
            e.getMessage();
        }
        return lastDependencyName;
    }

    private static String generateRandomArithmeticOperation() {
        String[] symbols = {"-", "+", "/", "*", "^"};
        return symbols[new Random().nextInt(symbols.length)];
    }
}
