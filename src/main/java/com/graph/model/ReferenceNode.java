package com.graph.model;

import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferenceNode)) return false;
        ReferenceNode that = (ReferenceNode) o;
        return Objects.equal(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }
}
