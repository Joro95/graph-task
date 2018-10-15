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

//    public static String generateData(GraphFacade graphFacade, int sizeOfData) {
//        String lastDependencyName = "";
//        try {
//            StringBuilder expression = new StringBuilder();
//            expression.append("A1=");
//            for (int i = 1; i < sizeOfData; i++) {
//                String reference = Converter.columnToLetters(i);
//                reference = reference.concat(1 + "");
//                StringBuilder innerExpression = new StringBuilder(reference + "=");
//                for (int j = 0; j < sizeOfData; j++) {
//                    String innerReference = Converter.columnToLetters(j);
//                    innerReference = innerReference.concat((i + 1) + "");
//                    StringBuilder nestedExpression = new StringBuilder(innerReference + "=");
//                    for (int k = j + 1; k < sizeOfData + 1; k++) {
//                        String nestedReference = Converter.columnToLetters(k);
//                        nestedReference = nestedReference.concat((i+1) + "");
//                        nestedExpression.append(nestedReference);
//                        nestedExpression.append(generateRandomArithmeticOperation());
//                    }
//                    nestedExpression.append("1");
////                    System.out.println(nestedExpression);
//                    graphFacade.processExpression(nestedExpression.toString());
//                    innerExpression.append(innerReference);
//                    innerExpression.append(generateRandomArithmeticOperation());
//                }
//                innerExpression.append("1");
////                System.out.println(innerExpression);
//                graphFacade.processExpression(innerExpression.toString());
//                expression.append(reference);
//                expression.append(generateRandomArithmeticOperation());
//            }
//            expression.append("1");
//            lastDependencyName = Converter.columnToLetters(sizeOfData);
//            for (int i = 1; i < sizeOfData; i++) {
//                String ref = lastDependencyName + i;
//                String nextCell = lastDependencyName + (i + 1);
////                System.out.println(ref + "=" + nextCell);
//                graphFacade.processExpression(ref + "=" + nextCell);
//            }
////            System.out.println(expression);
//            graphFacade.processExpression(expression.toString());
//        } catch (InvalidInputException | CircularDependenciesException | CellNotInitializedException | ParseException | InterruptedException | ExecutionException e) {
//            System.out.println("ERROR");
//            e.getMessage();
//        }
//        return lastDependencyName + sizeOfData + "";
//    }

    public static String generateData(GraphFacade graphFacade, int sizeOfData) throws CircularDependenciesException, InterruptedException, ParseException, InvalidInputException, ExecutionException, CellNotInitializedException {
        String dependencyName = "A1";
        StringBuilder expression = new StringBuilder();
        for (int i = 1; i < sizeOfData + 1; i++) {
            String reference = Converter.columnToLetters(1);
            String expressionReference = reference.concat(i + "=" + dependencyName);
            graphFacade.processExpression(expressionReference);
        }

        for (int i = 1; i < sizeOfData + 1; i++) {
            String reference = Converter.columnToLetters(2);
            String expressionReference = reference.concat(i + "=");
//            for (int j = 1; j < sizeOfData; j++) {
//                expressionReference = expressionReference.concat("B" + j + "+");
//            }
            expressionReference = expressionReference.concat("B" + i);
//            System.out.println(expressionReference);
            graphFacade.processExpression(expressionReference);
        }

        for (int i = 1; i < sizeOfData + 1; i++) {
            String reference = Converter.columnToLetters(3);
            String expressionReference = reference.concat(i + "=");
//            for (int j = 1; j < sizeOfData; j++) {
//                expressionReference = expressionReference.concat("C" + j + "+");
//            }
            expressionReference = expressionReference.concat("C" + i);
            graphFacade.processExpression(expressionReference);
        }

        String endPoint = "E1";
        String expr = endPoint + "=";
        for (int i = 1; i < sizeOfData; i++) {
            expr = expr.concat("D" + i + "+");
        }
        expr = expr.concat("D" + sizeOfData);
        graphFacade.processExpression(expr);

        return dependencyName;
    }

    private static String generateRandomArithmeticOperation() {

        String[] symbols = {"+"};
        return symbols[new Random().nextInt(symbols.length)];
    }

    public static void main(String[] args) throws CircularDependenciesException, InterruptedException, ParseException, InvalidInputException, ExecutionException, CellNotInitializedException {
        GraphFacade graphFacade = GraphFacade.getInstance();
        String s = TestDataGenerator.generateData(graphFacade, 4);
//        System.out.println(s);
//        System.out.println("END");
    }
}
