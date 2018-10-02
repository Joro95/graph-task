package com.graph.facade;

import com.graph.exception.InvalidInputException;
import com.graph.finder.SquareFinder;
import com.graph.model.Graph;
import com.graph.model.Node;
import com.graph.model.Square;
import com.graph.parser.ExpressionParser;
import com.graph.parser.InputParser;
import com.graph.validator.Validator;

public class GraphFacade {

    private static GraphFacade graphFacade;
    private SquareFinder squareFinder;
    private Graph graph;
    private ExpressionParser expressionParser;
    private InputParser inputParser;
    private GraphPrinter graphPrinter;

    private GraphFacade() {
        this.squareFinder = new SquareFinder();
        this.graph = new Graph();
        this.expressionParser = new ExpressionParser();
        this.inputParser = new InputParser();
        this.graphPrinter = new GraphPrinter();
    }

    public static synchronized GraphFacade getInstance(){
        if (graphFacade == null){
            graphFacade = new GraphFacade();
        }
        return graphFacade;
    }

    public void processExpression(String input) throws InvalidInputException {
        input = inputParser.parseInputToValidSyntax(input);
        Validator.checkForEqualsSign(input);

        String squareName = inputParser.getSquareName(input);
        String expression = inputParser.getExpression(input);

        Square square = squareFinder.getSquare(squareName, graph);
        Node expressionTree = expressionParser.parseExpression(expression);

        square.initializeSquare(expression, expressionTree);

    }



}
