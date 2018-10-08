package com.graph.parser;

import java.util.Stack;

public class ExpressionParser {

    public static String infixToPostfix(String expression) {
        String result = "";

        Stack<Character> stack = new Stack<>();


        for (int i = 0; i < expression.length(); ++i) {
            char currentCharacter = expression.charAt(i);

            // If the scanned character is an operand or a decimal delimiter, add it to output.
            if (Character.isLetterOrDigit(currentCharacter) || currentCharacter == '.') {
                result += currentCharacter;
            }
            // If the scanned character is a '(', push it to the stack.
            else if (currentCharacter == '(') {
                stack.push(currentCharacter);
            }
            //  If the scanned character is a ')', pop from the stack until a '(' is encountered.
            else if (currentCharacter == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.pop();
                }
                if (!stack.isEmpty() && stack.peek() != '(') {
                    return "Invalid Expression";
                } else {
                    stack.pop();
                }
            } else // an operator is encountered
            {
                while (!stack.isEmpty() && preceding(currentCharacter) <= preceding(stack.peek())) {
                    result += stack.pop();
                }
                stack.push(currentCharacter);
                result += " ";
            }

        }
        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    private static int preceding(char character) {
        switch (character) {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }
}
