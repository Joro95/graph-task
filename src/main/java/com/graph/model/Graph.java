package com.graph.model;

import java.util.ArrayList;

public class Graph {

    private ArrayList<ArrayList<Square>> graph;

    public Graph(){
        graph = new ArrayList<ArrayList<Square>>();
        for (int i = 0; i<702; i++){
            graph.add(new ArrayList<Square>());
            for (int j = 1; j<=999; j++){
                graph.get(i).add(new Square(columnRowToName(i, j), Square.Status.NOT_INITIALIZED));
            }
        }
    }

    public ArrayList<ArrayList<Square>> getGraph() {
        return graph;
    }

    public static String columnRowToName(int column, int row){
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

}
