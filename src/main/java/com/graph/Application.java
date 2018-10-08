package com.graph;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.InvalidInputException;
import com.graph.exception.ParseException;
import com.graph.facade.GraphFacade;
import com.graph.facade.GraphPrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args){
        GraphFacade graphFacade = GraphFacade.getInstance();
        GraphPrinter graphPrinter = new GraphPrinter();

        printWelcomePage();
        String titleGraph = getTitleGraph();

        while (true){
            System.out.println(titleGraph);
            System.out.println();
            graphPrinter.printGraph(graphFacade.getGraph());
            System.out.println();
            System.out.println();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter statement like <cell>=<expression>:");
            System.out.print("statement: ");
            String input = scanner.next();
            try {
                graphFacade.processExpression(input);
            } catch (InvalidInputException e) {
                System.out.println();
                System.out.println("******************************************************");
                System.out.println("*                   INVALID INPUT                    *");
                System.out.println("******************************************************");
                System.out.println();
            } catch (CircularDependenciesException e) {
                System.out.println();
                System.out.println("********************************************************************");
                System.out.println("*                   CIRCULAR DEPENDENCIES FOUND                    *");
                System.out.println("*                     ABORTING LAST OPERATION                      *");
                System.out.println("********************************************************************");
                System.out.println();
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println();
                System.out.println("********************************************************************");
                System.out.println("*                  THE PARSING WAS NOT COMPLETED                   *");
                System.out.println("*                     ABORTING LAST OPERATION                      *");
                System.out.println("********************************************************************");
                System.out.println();
            } catch (CellNotInitializedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printWelcomePage() {

        String banner = getBanner();
        System.out.println(banner);
        System.out.println("*********************************************************************************************");
        System.out.println();
        System.out.println(" Legend:");
        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("* N/I = Not Initialized                                                *");
        System.out.println("* ERROR = Error                                                        *");
        System.out.println("* OOB = Out Of Bounds (When number is too long to represent in square) *");
        System.out.println("************************************************************************");
        System.out.println();

    }

    private static String getTitleGraph() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/title.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "GRAPH";
    }

    private static String getBanner(){
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/banner.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "MISSING BANNER";
    }
}
