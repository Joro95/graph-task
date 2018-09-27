package com.graph.model;

import java.util.ArrayList;

public class Graph {

    private ArrayList<ArrayList<Square>> graph;

    public Graph(){
        graph = new ArrayList<ArrayList<Square>>();
        for (int i = 0; i<23; i++){
            graph.set(i, new ArrayList<Square>());
        }
    }
}
