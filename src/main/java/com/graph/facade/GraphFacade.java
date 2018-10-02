package com.graph.facade;

import com.graph.exception.InvalidInputException;
import com.graph.finder.SquareFinder;
import com.graph.model.Graph;
import com.graph.model.Node;
import com.graph.model.Square;
import com.graph.parser.ExpressionParser;

import static com.graph.parser.InputParser.*;
import static com.graph.validator.Validator.validateInputString;

public class GraphFacade {

    private static GraphFacade graphFacade;
    private SquareFinder squareFinder;
    private Graph graph;
    private ExpressionParser expressionParser;
    private GraphPrinter graphPrinter;

    private GraphFacade() {
        this.squareFinder = new SquareFinder();
        this.graph = new Graph();
        this.expressionParser = new ExpressionParser();
        this.graphPrinter = new GraphPrinter();
    }

    public static synchronized GraphFacade getInstance(){
        if (graphFacade == null){
            graphFacade = new GraphFacade();
        }
        return graphFacade;
    }

    public void processExpression(String input) throws InvalidInputException {
        validateInputString(input);

        String squareName = getSquareName(input);
        String expression = getExpression(input);

        Square square = squareFinder.getSquare(squareName, graph);
        Node expressionTree = expressionParser.parseExpression(expression);

        square.initializeSquare(expression, expressionTree);

    }

}
