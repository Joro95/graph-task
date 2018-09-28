package com.graph.finder;

import com.graph.exception.InvalidInputException;

public class SquareFinderUtils {


    public static final int COLUMN_MAX_SIZE = 2;
    public static final int ROW_MAX_SIZE = 3;

    /**
     * Formula is 26 * X + Y = columnNumber
     * 26 is the number of letters in the latin alphabet
     * X = first letter of the column name
     * Y = last letter of the column name
     */
    int getColumnNumber(String columnAsString) {
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

    int getFirstDigitOccurrence(String str){
        char[] characters = str.toCharArray();

        for (int i = 0; i < characters.length; i++){
            if (Character.isDigit(characters[i])){
                return i;
            }
        }
        return 0;
    }

    void validateInput(String column, String row) throws InvalidInputException {
        char[] columnChars = column.toCharArray();
        char[] rowChars = row.toCharArray();
        if (columnChars.length > COLUMN_MAX_SIZE || columnChars.length == 0 ||
                rowChars.length > ROW_MAX_SIZE || rowChars.length == 0){
            throw new InvalidInputException();
        }
        for(char c : columnChars){
            if (!Character.isLetter(c)){
                throw new InvalidInputException();
            }
        }
        for(char c : rowChars){
            if (!Character.isDigit(c)){
                throw new InvalidInputException();
            }
        }
    }
}
