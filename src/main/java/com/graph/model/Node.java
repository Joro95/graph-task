package com.graph.model;

public abstract class Node {
    private Node leftChildNode;
    private Node rightChildNode;
    private int result;

    public Node(Node leftChildNode, Node rightChildNode, int result) {
        this.leftChildNode = leftChildNode;
        this.rightChildNode = rightChildNode;
        this.result = result;
    }

    public Node getLeftChildNode() {
        return leftChildNode;
    }

    public void setLeftChildNode(Node leftChildNode) {
        this.leftChildNode = leftChildNode;
    }

    public Node getRightChildNode() {
        return rightChildNode;
    }

    public void setRightChildNode(Node rightChildNode) {
        this.rightChildNode = rightChildNode;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
