package com.graph.parser;

import com.graph.exception.InvalidInputException;
import com.graph.model.*;

import java.util.Stack;

import static com.graph.finder.SquareFinder.getSquare;

/**
 * Created by developer on 28/09/18.
 */
public class ExpressionTreeBuilder {

    public static final byte MINUS = -1;

    private ExpressionTreeBuilder()
    {
        throw new IllegalStateException();
    }

    public static boolean isOperator(char c) {
        if (c == '+' || c == '-'
                || c == '*' || c == '/'
                || c == '^') {
            return true;
        }
        return false;
    }

    // Returns root of constructed tree for given
    // postfix expression
    public static Node constructTree(String postfix, Graph graph) throws InvalidInputException {
        Stack<Node> st = new Stack<>();
        Node node = null, leftChildNode, rightChildNode;

        // Traverse through every character of
        // input expression
        for (int i = 0; i < postfix.length(); i++) {

            // If operand, simply push into stack
            char currentSymbol = postfix.charAt(i);
            if (!isOperator(currentSymbol) && currentSymbol != ' ') {
                //if reference -> use squareFinder to find square and assign it
                if(Character.isLetter(currentSymbol)) {
                   //if current symbol is alphabetic -> create substring from i to end
                    String substring = postfix.substring(i);
                    //iterate substring and create new string and append symbols until a symbol different from digit and alphabetic is found
                    String resultString = createReferenceName(substring);
                    //increment i with the length of the new string
                    i += resultString.length() - 1;
                    //find corresponding square by result string
                    Square square = getSquare(resultString, graph);
                    node = new ReferenceNode(square);
                }
                //if number -> parse string to double and assign it
                else if(Character.isDigit(currentSymbol)) {
                    //if current symbol is digit -> create substring from i to end
                    String substring = postfix.substring(i);
                    //iterate substring and create new string and append symbols until a symbol different from digit is found
                    String resultString = createNumber(substring);
                    //increment i with the length of the new string
                    i += resultString.length() - 1;
                    //create double from string value
                    //set double as the NumberNode's value
                    Double data = Double.valueOf(resultString);
                    node = new NumberNode(data);
                }

                st.push(node);
            } else if(isOperator(currentSymbol)) // operator -> parse string to char and assign it
            {
                node = new OperatorNode(currentSymbol);

                // Pop two top nodes
                // Store top
                rightChildNode = st.pop();      // Remove top
                leftChildNode = st.pop();

                //  make them children
                ((OperatorNode) node).setLeftChildNode(leftChildNode);
                ((OperatorNode) node).setRightChildNode(rightChildNode);

                // System.out.println(t1 + "" + t2);
                // Add this subexpression to stack
                st.push(node);
            }
        }

        //  only element will be root of expression
        // tree
        node = st.peek();
        st.pop();

        return node;
    }

    private static String createReferenceName(String substring) {
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i < substring.length(); i++) {
            if(!Character.isLetterOrDigit(substring.charAt(i))) {
                break;
            }
            sb.append(substring.charAt(i));
        }
        return sb.toString();
    }

    private static String createNumber(String substring) {
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i < substring.length(); i++) {
            if(!Character.isDigit(substring.charAt(i))) {
                break;
            }
            sb.append(substring.charAt(i));
        }
        return sb.toString();
    }

}
