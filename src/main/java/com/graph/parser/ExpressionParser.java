package com.graph.parser;

import java.util.Stack;

public class ExpressionParser {

    public static String infixToPostfix(String exp) {
        String result = "";

        Stack<Character> stack = new Stack<>();


        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);

            // If the scanned character is an operand or a decimal delimiter, add it to output.
            if (Character.isLetterOrDigit(c) || c == '.') {
                result += c;
            }
            // If the scanned character is a '(', push it to the stack.
            else if (c == '(') {
                stack.push(c);
            }
            //  If the scanned character is a ')', pop from the stack
            // until an '(' is encountered.
            else if (c == ')') {
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
                while (!stack.isEmpty() && preceding(c) <= preceding(stack.peek())) {
                    result += stack.pop();
                }
                stack.push(c);
                result += " ";
            }

        }
        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    private static int preceding(char ch) {
        switch (ch) {
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
