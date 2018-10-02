package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ParseException;

public class OperatorNode extends Node {

    private Node leftChildNode;
    private Node rightChildNode;
    private char data;

    public OperatorNode(Node leftChildNode, Node rightChildNode, char data) {
        this.leftChildNode = leftChildNode;
        this.rightChildNode = rightChildNode;
        this.data = data;
    }

    protected double calculateValue() throws ParseException, CellNotInitializedException {
        double leftChildValue = leftChildNode.calculateValue();
        double rightChildValue = rightChildNode.calculateValue();
        switch (data){
            case '+':
                return leftChildValue + rightChildValue;
            case '-':
                return leftChildValue - rightChildValue;
            case '*':
                return leftChildValue * rightChildValue;
            case '/':
                return leftChildValue / rightChildValue;
            case '^':
                return Math.pow(leftChildValue, rightChildValue);
            default:
                throw new ParseException();
        }
    }

    public Node getLeftChildNode() {
        return leftChildNode;
    }

    public Node getRightChildNode() {
        return rightChildNode;
    }
}
