package com.graph;

import com.graph.exception.InvalidInputException;
import com.graph.facade.GraphPrinter;
import com.graph.model.Graph;

public class Application {

    public static void main(String[] args) throws InvalidInputException {

        Graph graph = new Graph();
        GraphPrinter graphPrinter = new GraphPrinter();
        graphPrinter.printGraph(graph);



    }

}
