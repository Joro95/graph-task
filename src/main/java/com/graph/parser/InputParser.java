package com.graph.parser;

import static com.graph.parser.ExpressionTreeBuilder.isOperator;
import static java.lang.Character.isLetterOrDigit;

public class InputParser {

    private InputParser() {
        throw new IllegalStateException();
    }

    public static String getExpression(String input) {
        input = trimInput(input);
        return input.substring(input.indexOf('=') + 1, input.length());
    }

    public static String getSquareName(String input) {
        return input.substring(0, input.indexOf('='));
    }

    static String removeWhitespaces(String input) {
        return input.replace(" ", "");
    }

    static String replaceConsecutivePlusMinus(String input) {
        //if consecutive '+' and '-' -> if '-' count is an even number then replace substring with '+', if not -> replace substring with '-'
        StringBuilder sb = new StringBuilder(input);
        for(int i = 0; i < sb.length(); i++) {
            if(isPlusOrMinus(sb.charAt(i))){
                String stringOfPlusesAndMinuses = createPlusAndMinusSubstring(sb.substring(i));
                String symbolToReplaceWith = findEvenOrOddMinuses(stringOfPlusesAndMinuses);
                sb.delete(i, i + stringOfPlusesAndMinuses.length());
                sb.insert(i, symbolToReplaceWith);
            }
        }
        return sb.toString();
    }

    private static String findEvenOrOddMinuses(String stringOfPlusesAndMinuses) {
        int count = 0;
        for (int i = 0; i < stringOfPlusesAndMinuses.length(); i++) {
            if(stringOfPlusesAndMinuses.charAt(i) == '-') {
                count++;
            }
        }
        return count % 2 == 0 ? "+" : "-";
    }

    private static String createPlusAndMinusSubstring(String substring) {
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i < substring.length(); i++) {
            if(!isPlusOrMinus(substring.charAt(i))) {
                break;
            }
            sb.append(substring.charAt(i));
        }
        return sb.toString();
    }

    private static boolean isPlusOrMinus(char c) {
        return c == '+' || c == '-';
    }

    private static String trimInput(String input) {
        String result = input;
        result = replaceConsecutivePlusMinus(result);
        result = removeWhitespaces(result);
        result = refactorNegativeNumbersAndReferences(result);
        return result;
    }

    private static String refactorNegativeNumbersAndReferences(String input) {
        StringBuilder sb = new StringBuilder(input);
        char startingSymbol = sb.charAt(0);

        if (isOperator(input.charAt(0))) {
            sb.deleteCharAt(0);
            sb.insert(0, "(0" + startingSymbol);
            appendClosingBracketToNumber(3, sb);
        }
        for (int i = 0; i < sb.length(); i++) {
            char symbol = sb.charAt(i);
            if (isOperator(sb.charAt(i)) && sb.charAt(i - 1) != ')') {
                if (!isLetterOrDigit(sb.charAt(i - 1))) {
                    sb.deleteCharAt(i);
                    sb.insert(i, "(0" + symbol);
                    i += 3;
                    i = appendClosingBracketToNumber(i, sb);
                    if(i == sb.length()-1){
                        sb.append(')');
                    }
                }
            }
        }
        return sb.toString();
    }

    private static int appendClosingBracketToNumber(int index, StringBuilder sb) {
        for (int i = index; i < sb.length(); i++) {
            if (!isLetterOrDigit(sb.charAt(i))) {
                sb.insert(i, ")");
                return i + 1;
            }
        }
        return index;
    }
}
