package com.graph.converter;

import com.graph.exception.InvalidInputException;
import com.graph.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class Converter {

    private Converter(){
        throw new IllegalStateException();
    }

    public static String columnToLetters(int column){
        String columnAsLetters = "";
        int firstLetterNumber = column / 26;
        if (firstLetterNumber > 0){
            int firstLetterCode = firstLetterNumber + 64;
            char firstLetter = (char) firstLetterCode;
            columnAsLetters = columnAsLetters.concat(Character.toString(firstLetter));
        }
        int secondLetterNumber = column % 26;
        int secondLetterCode = secondLetterNumber + 65;
        char secondLetter = (char) secondLetterCode;

        columnAsLetters = columnAsLetters.concat(Character.toString(secondLetter));

        return columnAsLetters;
    }

    public static String rowColumnToName(int row, int column){
        String columnAsLetters = columnToLetters(column);
        return columnAsLetters.concat(Integer.toString(row));
    }

    public static Map<String, Integer> squareNameToColumnRow(String name) throws InvalidInputException {
        name = name.toUpperCase();
        int firstDigitOccurrence = getFirstDigitOccurrence(name);
        String columnAsString = name.substring(0, firstDigitOccurrence);
        String rowAsString = name.substring(firstDigitOccurrence, name.length());

        Validator.validateSquareName(rowAsString, columnAsString);

        int column = getColumnNumber(columnAsString);
        int row = Integer.parseInt(rowAsString) - 1;

        return getMapWithCoordinates(column, row);
    }

    private static int getColumnNumber(String columnAsString) {
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

    private static Map<String, Integer> getMapWithCoordinates(int column, int row) {
        Map<String, Integer> coordinates = new HashMap<>();
        coordinates.put("column", column);
        coordinates.put("row", row);
        return coordinates;
    }

    private static int getFirstDigitOccurrence(String str){
        char[] characters = str.toCharArray();

        for (int i = 0; i < characters.length; i++){
            if (Character.isDigit(characters[i])){
                return i;
            }
        }
        return 0;
    }

}
