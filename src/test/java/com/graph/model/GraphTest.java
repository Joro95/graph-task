package com.graph.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GraphTest {

    @Test
    public void graphConstructorTest(){
        Graph graph = new Graph();
        assertEquals("A1", graph.getGraph().get(0).get(0).getName());
        assertEquals("AA12", graph.getGraph().get(11).get(26).getName());
        for (ArrayList<Square> i : graph.getGraph()){
            for (Square square : i){
                assertNotNull(square);
                assertEquals(Square.Status.NOT_INITIALIZED, square.getStatus());
            }
        }
    }


}
