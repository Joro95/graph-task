package com.graph.model;

import com.graph.exception.CellNotInitializedException;

public class ReferenceNode implements Node {

    private Square data;
    
    public ReferenceNode(Square data) {
        this.data = data;
    }

    @Override
    public double calculateValue() throws CellNotInitializedException {
        if (data.getStatus() == Square.Status.INITIALIZED){
            return data.getValue();
        }
        throw new CellNotInitializedException();
    }

}
