package com.graph.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SquareTest {

    @Test
    public void initializeSquareWithValidExpression(){
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

    private Node createExpressionTree() {
        Square square = new Square("A2", Square.Status.INITIALIZED);
        square.initializeSquare("5", new NumberNode(5));
        Node refNode = new ReferenceNode(square);
        Node numNode = new NumberNode(5);
        Node numNode1 = new NumberNode(5);
        Node opNode = new OperatorNode(numNode, refNode, '*');
        return new OperatorNode(numNode1, opNode, '+');
    }

}
