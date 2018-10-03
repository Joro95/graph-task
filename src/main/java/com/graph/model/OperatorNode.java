package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ParseException;

public class OperatorNode implements Node {

    private Node leftChildNode;
    private Node rightChildNode;
    private char data;

    public OperatorNode(char data) {
        this.data = data;
    }

    @Override
    public double calculateValue() throws ParseException, CellNotInitializedException {
        //right child value always exists, however the same is not true for left child
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

    public void setLeftChildNode(Node leftChildNode) {
        this.leftChildNode = leftChildNode;
    }

    public void setRightChildNode(Node rightChildNode) {
        this.rightChildNode = rightChildNode;
    }

//    public static void main(String[] args) throws CellNotInitializedException, ParseException {
//        Node root = new OperatorNode('+');
//
//        Node leftChild = new NumberNode(-3.5);
//
//        Node rightChild = new OperatorNode('+');
//
//
//        ((OperatorNode) root).setLeftChildNode(leftChild);
//        ((OperatorNode) root).setRightChildNode(rightChild);
//
//        System.out.println(root.calculateValue());
//    }


    @Override
    public String toString() {
        return "OperatorNode{" +
                "leftChildNode=" + leftChildNode +
                ", rightChildNode=" + rightChildNode +
                ", data=" + data +
                '}';
    }
}
