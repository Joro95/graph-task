package com.graph;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.InvalidInputException;
import com.graph.exception.ParseException;
import com.graph.facade.GraphPrinter;
import com.graph.model.Graph;
import com.graph.model.Node;
import com.graph.parser.ExpressionParser;
import com.graph.parser.ExpressionTreeBuilder;
import com.graph.parser.InputParser;


public class Application {

    public static void main(String[] args) throws InvalidInputException, CellNotInitializedException, ParseException {

        Graph graph = new Graph();
        GraphPrinter graphPrinter = new GraphPrinter();
//        graphPrinter.printGraph(graph);

//        String infixExpr = "-35+(43-34)/2";
        String exp = "-35*2^(2*2)/-3";
//        String complicatedExpression = "-3*-2^-3/2";
//        String exp1 = "(3+(3+3))-3";
        String parsedString = InputParser.changeStartingSigns(exp);
        String postfix = ExpressionParser.infixToPostfix(parsedString);
        System.out.println(postfix);
        Node n = ExpressionTreeBuilder.constructTree(postfix, graph);
//        System.out.println(n);
        System.out.println(n.calculateValue());
        System.out.println();
    }

}
