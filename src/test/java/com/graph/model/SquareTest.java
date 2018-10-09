package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
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
        squareB.addDependency(squareA);
        squareB.addDependency(squareC);
        squareA.addDependency(squareC);
        squareA.addObserver(squareB);
        squareC.addObserver(squareA);
        squareC.addObserver(squareB);
        Node expressionTreeB = createExpressionTreeForLeaf(squareA, squareC);
        Node expressionTreeA = spy(new ReferenceNode(squareC));
        Node expressionTreeC = spy(new NumberNode(5));

        //Act
        squareB.initializeSquare("A1+C1", expressionTreeB);
        squareA.initializeSquare("C1", expressionTreeA);
        squareC.initializeSquare("5", expressionTreeC);

        //Assert
        verify(expressionTreeA).calculateValue();
        verify(expressionTreeB).calculateValue();
        verify(expressionTreeC).calculateValue();
        //The following asserts are to verify that the hashCode method hasn't been modified
        assertEquals(2095, squareA.hashCode());
        assertEquals(2126, squareB.hashCode());
        assertEquals(2157, squareC.hashCode());
    }

    @Test
    public void checkForCircularDependenciesNoException() throws CircularDependenciesException {
        //Arrange
        Square square = new Square("A1", Square.Status.NOT_INITIALIZED);
        Square square1 = new Square("A2", Square.Status.NOT_INITIALIZED);
        Square square2 = new Square("A3", Square.Status.NOT_INITIALIZED);
        square.addObserver(square1);

        //Act
        square.checkForCircularDependencies(square2);
    }

    @Test(expected = CircularDependenciesException.class)
    public void checkForCircularDependenciesThrowsException() throws CircularDependenciesException {
        //Arrange
        Square square = new Square("A1", Square.Status.NOT_INITIALIZED);
        Square square1 = new Square("A2", Square.Status.NOT_INITIALIZED);
        Square square2 = new Square("A3", Square.Status.NOT_INITIALIZED);
        square.addObserver(square1);
        square1.addObserver(square2);

        //Act
        square.checkForCircularDependencies(square2);
    }

    @Test(expected = CircularDependenciesException.class)
    public void checkForCircularDependenciesThrowsExceptionWhenIsSelfReferencedException() throws CircularDependenciesException {
        //Arrange
        Square square = new Square("A1", Square.Status.NOT_INITIALIZED);

        //Act
        square.checkForCircularDependencies(square);
    }

    private Node createExpressionTreeForLeaf(Square square1, Square square2) {
        Node node = spy(new OperatorNode('+'));
        ((OperatorNode) node).setLeftChildNode(new ReferenceNode(square1));
        ((OperatorNode) node).setRightChildNode(new ReferenceNode(square2));
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
