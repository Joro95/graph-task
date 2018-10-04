package com.graph.validator;

import com.graph.exception.InvalidInputException;

public class Validator {

    private static final int COLUMN_MAX_SIZE = 2;
    private static final int ROW_MAX_SIZE = 3;

    public static void checkForEqualsSign(String input) throws InvalidInputException {
        if (!input.contains("=")){
            throw new InvalidInputException();
        }
    }

    public static void validateSquareName(String row, String column) throws InvalidInputException {
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

    public static String checkBracketPairs(String input) {
        //TODO
        //if input has incorrect number of opening and closing brackets -> throw exception
        return input;
    }

    public static String checkConsecutiveSymbols(String input) {
        //TODO
        //if input has consecutive '/', '*' or '^' -> throw exception
        return input;
    }

    public static String checkForIllegalSigns(String input) {
        //TODO
        //if there are illegal signs in the input -> throw exception
        return input;
    }

    public static void validateInputString(String input) throws InvalidInputException {
        checkForEqualsSign(input);
        checkBracketPairs(input);
        checkConsecutiveSymbols(input);
        checkForIllegalSigns(input);
    }
}
