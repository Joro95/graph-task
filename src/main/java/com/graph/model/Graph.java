package com.graph.model;

import java.util.ArrayList;
import java.util.List;

import static com.graph.converter.Converter.rowColumnToName;

public class Graph {

    private ArrayList<ArrayList<Square>> graph;

    public Graph(){
        graph = new ArrayList<>();
        for (int i = 1; i<999; i++){
            graph.add(new ArrayList<>());
            for (int j = 0; j<=702; j++){
                graph.get(i-1).add(new Square(rowColumnToName(i, j), Square.Status.NOT_INITIALIZED));
            }
        }
    }

    public List<ArrayList<Square>> getGraph() {
        return graph;
    }

}
