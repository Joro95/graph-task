package com.graph.parser;

public class InputParser {

    private InputParser() {
        throw new IllegalStateException();
    }

    public static String getSquareName(String input) {
        return input.substring(0, input.indexOf('='));
    }

    public static String removeWhitespaces(String input) {
        return input.replace(" ", "");
    }

    public static String replaceConsecutivePlusMinus(String input) {
        //TODO
        //if consecutive '+' and '-' -> if '-' count is an even number then replace substring with '+', if not -> replace substring with '-'
        String parsedInput = input;
        return parsedInput;
    }

    public static String trimInput(String input) {
        String result = new String(input);
        result = replaceConsecutivePlusMinus(result);
        result = removeWhitespaces(result);
        return result;
    }

    public static String getExpression(String input) {
        input = trimInput(input);
        return input.substring(input.indexOf('=') + 1, input.length());
    }
}
