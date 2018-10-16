package com.graph.traverser;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ExpressionCalculationException;
import com.graph.exception.ParseException;
import com.graph.model.Node;
import com.graph.model.NumberNode;
import com.graph.model.OperatorNode;
import com.graph.model.Square;

import java.util.Stack;

/**
 * Created by developer on 16/10/18.
 */
public class ExpressionTreeTraverser {
    private ExpressionTreeTraverser() {
        throw new IllegalStateException();
    }

    public static void calculateSquareValue(Square square) throws CellNotInitializedException, ParseException {
        Stack<Node> stack = constructStackFromTree(square.getExpressionTree());
        double result = calculateFromStack(stack);
        square.setValue(result);
    }

    private static Stack<Node> constructStackFromTree(Node root) {
        Stack<Node> helperStack = new Stack<>();
        Stack<Node> expressionNodeStack = new Stack<>();

        helperStack.push(root);
        while (!helperStack.isEmpty()) {
            Node temporaryNode = helperStack.pop();
            expressionNodeStack.push(temporaryNode);
            if(temporaryNode instanceof OperatorNode) {
                helperStack.push(((OperatorNode) temporaryNode).getLeftChildNode());
                helperStack.push(((OperatorNode) temporaryNode).getRightChildNode());
            }
        }
        return expressionNodeStack;
    }

    private static double calculateFromStack(Stack<Node> stack) throws ParseException, CellNotInitializedException {
        while(stack.size() != 1) {
            if(!(stack.peek() instanceof OperatorNode)) {
                Node firstOperand = stack.pop();
                Node secondOperand = stack.pop();
                if(!(stack.peek() instanceof OperatorNode)) {
                    Node thirdOperand = stack.pop();
                    popPushAndGo(stack, secondOperand, thirdOperand);
                    stack.push(firstOperand);
                } else {
                    popPushAndGo(stack, firstOperand, secondOperand);
                }
            }
        }

        return stack.pop().calculateValue();
    }

    private static void popPushAndGo(Stack<Node> stack, Node secondOperand, Node thirdOperand) throws ParseException, CellNotInitializedException {
        OperatorNode operatorNode = (OperatorNode) stack.pop();
        double data = calculateOperatorNodeValue(secondOperand, thirdOperand, operatorNode);
        NumberNode newNumberNode = new NumberNode(data);
        stack.push(newNumberNode);
    }





    private static double calculateOperatorNodeValue(Node firstOperand, Node secondOperand, OperatorNode operatorNode) throws ParseException, CellNotInitializedException {
        char data = operatorNode.getData();
        double leftChildValue = firstOperand.calculateValue();
        double rightChildValue = secondOperand.calculateValue();
        switch (data){
            case '+':
                return leftChildValue + rightChildValue;
            case '-':
                return leftChildValue - rightChildValue;
            case '*':
                return leftChildValue * rightChildValue;
            case '/':
                if(leftChildValue == 0 || rightChildValue == 0){
                    throw new ExpressionCalculationException();
                }
                double result = leftChildValue / rightChildValue;
                if (Double.isInfinite(result) || Double.isNaN(result)){
                    throw new ExpressionCalculationException();
                }
                return result;
            case '^':
                return Math.pow(leftChildValue, rightChildValue);
            default:
                throw new ParseException();
        }
    }

}
