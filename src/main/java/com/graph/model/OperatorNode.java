package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ExpressionCalculationException;
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
                if(leftChildValue == 0 || rightChildValue == 0){
                    throw new ExpressionCalculationException();
                }
                double result = leftChildValue / rightChildValue;
                if (Double.isInfinite(result) || Double.isNaN(result)){
                    throw new ExpressionCalculationException();
                }
                return result;
            case '^':
                return Math.pow(leftChildValue, rightChildValue);
            default:
                throw new ParseException();
        }
    }

    public void setLeftChildNode(Node leftChildNode) {
        this.leftChildNode = leftChildNode;
    }

    public void setRightChildNode(Node rightChildNode) {
        this.rightChildNode = rightChildNode;
    }
    
    @Override
    public String toString() {
        return "OperatorNode{" +
                "leftChildNode=" + leftChildNode +
                ", rightChildNode=" + rightChildNode +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperatorNode)) return false;
        OperatorNode that = (OperatorNode) o;
        return data == that.data &&
                com.google.common.base.Objects.equal(leftChildNode, that.leftChildNode) &&
                com.google.common.base.Objects.equal(rightChildNode, that.rightChildNode);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(leftChildNode, rightChildNode, data);
    }
}
