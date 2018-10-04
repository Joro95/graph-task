package com.graph;

import com.graph.exception.InvalidInputException;
import com.graph.facade.GraphFacade;
import com.graph.facade.GraphPrinter;

import java.util.Scanner;

public class Application {

    public static void main(String[] args){
        GraphFacade graphFacade = GraphFacade.getInstance();
        GraphPrinter graphPrinter = new GraphPrinter();

        while (true){
            graphPrinter.printGraph(graphFacade.getGraph());
            System.out.println();
            System.out.println();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.next();
                try {
                    graphFacade.processExpression(input);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println();
                    System.out.println("******************* INVALID INPUT ********************");
                    System.out.println();
                }
            }
        }

    }

}
