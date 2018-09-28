package com.graph.finder;

import com.graph.exception.InvalidInputException;
import com.graph.model.Graph;
import com.graph.model.Square;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SquareFinder {

    private SquareFinderUtils squareFinderUtils;

    public SquareFinder(){
        this.squareFinderUtils = new SquareFinderUtils();
    }

    public Square getSquare(String name, Graph graph) throws InvalidInputException {
        Map<String, Integer> coordinates = squareNameToColumnRow(name);
        List<Square> column = graph.getGraph().get(coordinates.get("column"));
        return column.get(coordinates.get("row"));
    }

    Map<String, Integer> squareNameToColumnRow(String name) throws InvalidInputException {
        name = name.toUpperCase();
        int firstDigitOccurrence = squareFinderUtils.getFirstDigitOccurrence(name);
        String columnAsString;
        String rowAsString;
        columnAsString = name.substring(0, firstDigitOccurrence);
        rowAsString = name.substring(firstDigitOccurrence, name.length());

        squareFinderUtils.validateInput(columnAsString, rowAsString);

        int column = squareFinderUtils.getColumnNumber(columnAsString);
        int row = Integer.parseInt(rowAsString) - 1;

        return getMapWithCoordinates(column, row);
    }

    private Map<String, Integer> getMapWithCoordinates(int column, int row) {
        Map<String, Integer> coordinates = new HashMap<String, Integer>();
        coordinates.put("column", column);
        coordinates.put("row", row);
        return coordinates;
    }
}
