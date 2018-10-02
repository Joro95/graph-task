package com.graph.parser;

import com.graph.model.Square;

public class InputParser {


    public String getSquareName(String input) {
        return input.substring(0, input.indexOf('='));
    }

    public String parseInputToValidSyntax(String input) {
        String result = input.replace(" ", "");
        return result;
    }

    public String getExpression(String input) {
        return input.substring(input.indexOf('='), input.length());
    }
}
