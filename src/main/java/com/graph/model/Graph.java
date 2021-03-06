package com.graph.model;

import java.util.ArrayList;
import java.util.List;

import static com.graph.converter.Converter.rowColumnToName;

public class Graph {

    private ArrayList<ArrayList<Square>> tableOfSquares;

    public Graph(){
        tableOfSquares = new ArrayList<>();
        for (int i = 1; i<20000; i++){
            tableOfSquares.add(new ArrayList<>());
            for (int j = 0; j<=10; j++){
                tableOfSquares.get(i-1).add(new Square(rowColumnToName(i, j), Square.Status.NOT_INITIALIZED));
            }
        }
    }

    public List<ArrayList<Square>> getTableOfSquares() {
        return tableOfSquares;
    }

}
