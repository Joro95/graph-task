package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ParseException;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SquareTest {

    @Test
    public void initializeSquareWithValidExpression() throws InterruptedException, ExecutionException {
        //Arrange
        Square square = new Square("A1", Square.Status.NOT_INITIALIZED);
        Node expressionTree = createExpressionTree();

        //Act
        square.initializeSquare("5+5*A2", expressionTree);

        //Assert
        assertEquals(Square.Status.INITIALIZED, square.getStatus());
        assertEquals(30.0, square.getValue(), 1);
        assertEquals("5+5*A2", square.getExpression());
    }

    @Test
    public void calculateSquareWithoutUnnecessaryCalculations() throws CellNotInitializedException, ParseException, InterruptedException, ExecutionException {
        //Arrange
        Square squareA = new Square("A1", Square.Status.NOT_INITIALIZED);
        Square squareB = new Square("B1", Square.Status.NOT_INITIALIZED);
        Square squareC = new Square("C1", Square.Status.NOT_INITIALIZED);
        squareA.addDependency(squareB);
        squareA.addDependency(squareC);
        squareB.addDependency(squareC);
        squareB.addObserver(squareA);
        squareC.addObserver(squareA);
        squareC.addObserver(squareB);
        Node expressionTreeA = createExpressionTreeForA(squareB, squareC);
        Node expressionTreeB = spy(new ReferenceNode(squareC));
        Node expressionTreeC = spy(new NumberNode(5));

        //Act
        squareA.initializeSquare("B1+C1", expressionTreeA);
        squareB.initializeSquare("C1", expressionTreeB);
        squareC.initializeSquare("5", expressionTreeC);

        //Assert
        verify(expressionTreeA).calculateValue();
        verify(expressionTreeC).calculateValue();
        verify(expressionTreeB).calculateValue();

    }

    private Node createExpressionTreeForA(Square squareB, Square squareC) {
        Node node = spy(new OperatorNode('+'));
        ((OperatorNode) node).setLeftChildNode(new ReferenceNode(squareB));
        ((OperatorNode) node).setRightChildNode(new ReferenceNode(squareC));
        return node;
    }

    private Node createExpressionTree() throws InterruptedException, ExecutionException {
        Square square = new Square("A2", Square.Status.INITIALIZED);
        square.initializeSquare("5", new NumberNode(5));
        Node refNode = new ReferenceNode(square);
        Node numNode = new NumberNode(5);
        Node numNode1 = new NumberNode(5);
        Node opNode = new OperatorNode('*');

        ((OperatorNode) opNode).setLeftChildNode(numNode);
        ((OperatorNode) opNode).setRightChildNode(refNode);

        OperatorNode operatorNode = new OperatorNode('+');

        operatorNode.setRightChildNode(opNode);
        operatorNode.setLeftChildNode(numNode1);
        return operatorNode;
    }


}
