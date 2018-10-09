package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ExpressionCalculationException;
import com.graph.exception.ParseException;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class NodeTest {

    @Test
    public void numberNodeCalculateValueTest() throws CellNotInitializedException, ParseException, ExpressionCalculationException {
        //Arrange
        Node node = new NumberNode(5);

        //Act
        double result = node.calculateValue();

        //Assert
        assertEquals(5, result, 0.001);
    }

    @Test
    public void referenceNodeCalculateValueTest() throws CellNotInitializedException, ParseException, ExpressionCalculationException, InterruptedException, ExecutionException {
        //Arrange
        Square square = new Square("A1", Square.Status.INITIALIZED);
        square.initializeSquare("", new NumberNode(5));
        Node node = new ReferenceNode(square);

        //Act
        double result = node.calculateValue();

        //Assert
        assertEquals(5, result, 0.001);
    }

    @Test(expected = CellNotInitializedException.class)
    public void referenceNodeCalculateValueThrowsExceptionTest() throws CellNotInitializedException, ParseException, ExpressionCalculationException {
        //Arrange
        Square square = new Square("A1", Square.Status.NOT_INITIALIZED);
        Node node = new ReferenceNode(square);

        //Act
        node.calculateValue();

        //Assert
    }

    @Test
    public void operatorNodeCalculatePlusValueTest() throws CellNotInitializedException, ParseException, ExpressionCalculationException {
        //Arrange
        Node left = new NumberNode(5);
        Node right = new NumberNode(5);
        Node node = new OperatorNode('+');
        ((OperatorNode) node).setRightChildNode(right);
        ((OperatorNode) node).setLeftChildNode(left);

        //Act
        double result = node.calculateValue();

        //Assert
        assertEquals(10, result, 0.001);
    }

    @Test
    public void operatorNodeCalculateMinusValueTest() throws CellNotInitializedException, ParseException, ExpressionCalculationException {
        //Arrange
        Node left = new NumberNode(5);
        Node right = new NumberNode(5);
        Node node = new OperatorNode('-');
        ((OperatorNode) node).setRightChildNode(right);
        ((OperatorNode) node).setLeftChildNode(left);

        //Act
        double result = node.calculateValue();

        //Assert
        assertEquals(0, result, 0.001);
    }

    @Test
    public void operatorNodeCalculateMultiplyValueTest() throws CellNotInitializedException, ParseException, ExpressionCalculationException {
        //Arrange
        Node left = new NumberNode(5);
        Node right = new NumberNode(5);
        Node node = new OperatorNode('*');
        ((OperatorNode) node).setRightChildNode(right);
        ((OperatorNode) node).setLeftChildNode(left);

        //Act
        double result = node.calculateValue();

        //Assert
        assertEquals(25, result, 0.001);
    }

    @Test
    public void operatorNodeCalculateDivideValueTest() throws CellNotInitializedException, ParseException, ExpressionCalculationException {
        //Arrange
        Node left = new NumberNode(5);
        Node right = new NumberNode(5);
        Node node = new OperatorNode('/');
        ((OperatorNode) node).setRightChildNode(right);
        ((OperatorNode) node).setLeftChildNode(left);

        //Act
        double result = node.calculateValue();

        //Assert
        assertEquals(1, result, 0.001);
    }

    @Test
    public void operatorNodeCalculatePowerValueTest() throws CellNotInitializedException, ParseException, ExpressionCalculationException {
        //Arrange
        Node left = new NumberNode(5);
        Node right = new NumberNode(5);
        Node node = new OperatorNode('^');
        ((OperatorNode) node).setRightChildNode(right);
        ((OperatorNode) node).setLeftChildNode(left);

        //Act
        double result = node.calculateValue();

        //Assert
        assertEquals(3125, result, 0.001);
    }

    @Test(expected = ParseException.class)
    public void operatorNodeCalculateValueThrowsExceptionTest() throws CellNotInitializedException, ParseException, ExpressionCalculationException {
        //Arrange
        Node left = new NumberNode(5);
        Node right = new NumberNode(5);
        Node node = new OperatorNode('1');
        ((OperatorNode) node).setRightChildNode(right);
        ((OperatorNode) node).setLeftChildNode(left);

        //Act
        node.calculateValue();
    }
}
