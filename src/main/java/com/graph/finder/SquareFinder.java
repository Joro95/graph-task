package com.graph.finder;

import com.graph.exception.InvalidInputException;
import com.graph.model.Graph;
import com.graph.model.Square;

import java.util.List;
import java.util.Map;

import static com.graph.converter.Converter.squareNameToColumnRow;

public class SquareFinder {

    private SquareFinder() {
        throw new IllegalStateException();
    }

    public static Square getSquare(String name, Graph graph) throws InvalidInputException {
        Map<String, Integer> coordinates = squareNameToColumnRow(name);
        List<Square> columns = graph.getGraph().get(coordinates.get("row"));
        return columns.get(coordinates.get("column"));
    }
  
    static Map<String, Integer> squareNameToColumnRow(String name) throws InvalidInputException {
        name = name.toUpperCase();
        int firstDigitOccurrence = getFirstDigitOccurrence(name);
        String columnAsString = name.substring(0, firstDigitOccurrence);
        String rowAsString = name.substring(firstDigitOccurrence, name.length());

        Validator.validateSquareName(rowAsString, columnAsString);

        int column = getColumnNumber(columnAsString);
        int row = Integer.parseInt(rowAsString) - 1;

        return constructMapWithCoordinates(column, row);
    }

    static private Map<String, Integer> constructMapWithCoordinates(int column, int row) {
        Map<String, Integer> coordinates = new HashMap<>();
        coordinates.put("column", column);
        coordinates.put("row", row);
        return coordinates;
    }

    static int getColumnNumber(String columnAsString) {
        char[] columnChars = columnAsString.toCharArray();
        int firstNumber = 0;
        int secondNumber;
        if (columnAsString.length() > 1){
            firstNumber = 26 * (columnChars[0] - 64);
            secondNumber = columnChars[1] - 65;
        } else {
            secondNumber = columnChars[0] - 65;
        }
        return firstNumber + secondNumber;
    }

    static int getFirstDigitOccurrence(String str){
        char[] characters = str.toCharArray();

        for (int i = 0; i < characters.length; i++){
            if (Character.isDigit(characters[i])){
                return i;
            }
        }
        return 0;
    }
}
