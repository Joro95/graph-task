package com.graph.parser;

import com.graph.model.Node;
import com.graph.model.NumberNode;

import java.util.Stack;

public class ExpressionParser implements ExpressionParserInterface {

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

    // to postfix expression.
    public static String infixToPostfix(String exp) {
        // initializing empty String for result
        String result = "";

        // initializing empty stack
        Stack<Character> stack = new Stack<>();


        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);

            // If the scanned character is an operand, add it to output.
            if (Character.isLetterOrDigit(c) || c == '.') {
                result += c;
            }
            // If the scanned character is an '(', push it to the stack.
            else if (c == '(') {
                stack.push(c);
            }
            //  If the scanned character is an ')', pop and output from the stack
            // until an '(' is encountered.
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.pop();
                }
                if (!stack.isEmpty() && stack.peek() != '(') {
                    return "Invalid Expression"; // invalid expression
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

    public Node parseExpression(String expression) {
        return new NumberNode(5);
    }
}
