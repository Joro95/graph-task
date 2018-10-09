package com.graph.validator;

import com.graph.exception.InvalidInputException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.regex.Pattern;

public class Validator {

    private static final int COLUMN_MAX_SIZE = 2;
    private static final int ROW_MAX_SIZE = 3;

    private Validator(){
        throw new IllegalStateException();
    }

    public static void validateInputString(String input) throws InvalidInputException {
        checkForEqualsSign(input);
        checkBracketPairs(input);
        checkConsecutiveSymbols(input);
        checkForIllegalSigns(input);
    }

    public static void validateSquareName(String row, String column) throws InvalidInputException {
        char[] columnChars = column.toCharArray();
        char[] rowChars = row.toCharArray();
        if (columnChars.length > COLUMN_MAX_SIZE || columnChars.length == 0 ||
                rowChars.length > ROW_MAX_SIZE || rowChars.length == 0) {
            throw new InvalidInputException();
        }
        for (char c : columnChars) {
            if (!Character.isLetter(c)) {
                throw new InvalidInputException();
            }
        }
        for (char c : rowChars) {
            if (!Character.isDigit(c)) {
                throw new InvalidInputException();
            }
        }
    }

    private static void checkForEqualsSign(String input) throws InvalidInputException {
        if (!input.contains("=")) {
            throw new InvalidInputException();
        }
    }

    private static void checkBracketPairs(String input) throws InvalidInputException {
        //if input has incorrect number of opening and closing brackets -> throw exception
        Deque<Character> brackets = new ArrayDeque<>();
        try {
            for (int i = 0; i < input.length(); i++) {
                char symbol = input.charAt(i);
                if (symbol == '(') {
                    brackets.push(symbol);
                }
                if (symbol == ')') {
                    brackets.pop();
                }
            }
            if(!brackets.isEmpty()) {
                throw new InvalidInputException();
            }
        } catch (EmptyStackException e) {
            throw new InvalidInputException();
        }
    }

    private static void checkConsecutiveSymbols(String input) throws InvalidInputException {
        //if input has consecutive '/', '*' or '^' -> throw exception
        for (int i = 0; i < input.length(); i++) {
            if ((i < input.length() - 1 && isSymbol(input.charAt(i)) && isSymbol(input.charAt(i + 1)))
                    || i > 0 && isSymbol(input.charAt(i)) && isSymbol(input.charAt(i - 1))) {
                throw new InvalidInputException();
            }
        }
    }

    private static boolean isSymbol(char c) {
        return c == '/' || c == '*' || c == '^';
    }

    private static void checkForIllegalSigns(String input) throws InvalidInputException {
        //if there are illegal signs in the input -> throw exception
        if (!Pattern.matches("[a-zA-Z0-9+\\-*/()^=]*", input)) {
            throw new InvalidInputException();
        }
    }
}
