package com.graph.facade;

import com.graph.exception.CircularDependenciesException;
import com.graph.exception.InvalidInputException;
import com.graph.model.Graph;
import com.graph.model.Node;
import com.graph.model.Square;

import static com.graph.finder.SquareFinder.getSquare;
import static com.graph.parser.ExpressionParser.infixToPostfix;
import static com.graph.parser.ExpressionTreeBuilder.constructTree;
import static com.graph.parser.InputParser.*;
import static com.graph.validator.Validator.validateInputString;

public class GraphFacade {

    private static GraphFacade graphFacade;
    private Graph graph;

    private GraphFacade() {
        this.graph = new Graph();
    }

    public static synchronized GraphFacade getInstance(){
        if (graphFacade == null){
            graphFacade = new GraphFacade();
        }
        return graphFacade;
    }

    public void processExpression(String input) throws InvalidInputException, CircularDependenciesException {
        validateInputString(input);

        String squareName = getSquareName(input);
        String expression = getExpression(input);

        Square square = getSquare(squareName, graph);
        String postFix = infixToPostfix(expression);
        Node expressionTree = constructTree(postFix, graph, square);

        square.initializeSquare(expression, expressionTree);
    }

    public Graph getGraph() {
        return graph;
    }
}
