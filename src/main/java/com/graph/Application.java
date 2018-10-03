package com.graph;

import com.graph.facade.GraphPrinter;
import com.graph.model.Graph;
import com.graph.model.Node;
import com.graph.model.NumberNode;

public class Application {

    public static void main(String[] args){

        Graph graph = new Graph();
        GraphPrinter graphPrinter = new GraphPrinter();
        Node node = new NumberNode(1235);
        Node node1 = new NumberNode(23.55);
        graph.getGraph().get(0).get(0).initializeSquare("", node);
        graph.getGraph().get(9).get(27).initializeSquare("", node1);
        graphPrinter.printGraph(graph);

    }

}
