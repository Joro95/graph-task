package com.graph.model;

import java.util.ArrayList;

public class Graph {

    private ArrayList<ArrayList<Square>> graph;

    public Graph(){
        graph = new ArrayList<ArrayList<Square>>();
        for (int i = 1; i<999; i++){
            graph.add(new ArrayList<Square>());
            for (int j = 0; j<=702; j++){
                graph.get(i-1).add(new Square(this.rowColumnToName(i, j), Square.Status.NOT_INITIALIZED));
            }
        }
    }

    private String rowColumnToName(int row, int column){
        String coordinates = "";
        int firstLetterNumber = column / 26;
        if (firstLetterNumber > 0){
            int firstLetterCode = firstLetterNumber + 64;
            char firstLetter = (char) firstLetterCode;
            coordinates = coordinates.concat(Character.toString(firstLetter));
        }
        int secondLetterNumber = column % 26;
        int secondLetterCode = secondLetterNumber + 65;
        char secondLetter = (char) secondLetterCode;

        coordinates = coordinates.concat(Character.toString(secondLetter));
        coordinates = coordinates.concat(Integer.toString(row));

        return coordinates;
    }

    public ArrayList<ArrayList<Square>> getGraph() {
        return graph;
    }

}
