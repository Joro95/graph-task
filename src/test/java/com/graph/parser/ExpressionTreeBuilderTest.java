package com.graph.parser;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.InvalidInputException;
import com.graph.exception.ParseException;
import com.graph.model.*;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;

/**
 * Created by developer on 05/10/18.
 */
public class ExpressionTreeBuilderTest {

    @Test
    public void createNumber_creates_number_from_substring() throws Exception {
        String expected = "56328";
        String input = "56328+B3-C";
        String actual = Whitebox.invokeMethod(ExpressionTreeBuilder.class, "createNumber", input);

        assertEquals("The method does not create a number properly from a string value", expected, actual);
    }

    @Test
    public void createReferenceName_creates_number_from_substring() throws Exception {
        String expected = "B5";
        String input = "B5+6328+B3-C";
        String actual = Whitebox.invokeMethod(ExpressionTreeBuilder.class, "createReferenceName", input);

        assertEquals("The method does not create a number properly from a string value", expected, actual);
    }

    @Test
    public void isOperator_returns_true_if_character_is_an_operator() {
        boolean expected = true;
        char input = '*';
        boolean actual = ExpressionTreeBuilder.isOperator(input);

        assertEquals("The result should be true when symbol is an operator, but it is false", expected, actual);
    }

    @Test
    public void isOperator_returns_false_if_character_is_not_an_operator() {
        boolean expected = false;
        char input = '#';
        boolean actual = ExpressionTreeBuilder.isOperator(input);

        assertEquals("The result should be false when symbol is not an operator, but it is false", expected, actual);
    }

    @Test
    public void constructTree_returns_root_node_of_tree_from_give_expression() throws CircularDependenciesException, CellNotInitializedException, ParseException, InvalidInputException {
        Graph graph = new Graph();
        Square observer = new Square("A1", Square.Status.INITIALIZED);
        Node root = new OperatorNode('+');

        Node leftChildNodeOfRoot = new NumberNode(5.0);
        Node rightChildNodeOfRoot = new NumberNode(10.0);
        ((OperatorNode) root).setLeftChildNode(leftChildNodeOfRoot);
        ((OperatorNode) root).setRightChildNode(rightChildNodeOfRoot);

        String input = "5 10+";

        NumberNode optimizedRootNode = new NumberNode(15.0);

        Node actualRoot = ExpressionTreeBuilder.constructTree(input, graph, observer);

        assertEquals("The tree constructed is not as expected", optimizedRootNode, actualRoot);
    }
}
