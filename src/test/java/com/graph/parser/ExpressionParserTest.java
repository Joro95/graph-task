package com.graph.parser;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;

/**
 * Created by developer on 05/10/18.
 */
public class ExpressionParserTest {

    @Test
    public void infixToPostfix_returns_correct_postifx_expression() {
        String expectedPostfixExpression = "3 3+";
        String input = "3+3";
        String actual = ExpressionParser.infixToPostfix(input);

        assertEquals("The expression was not converted into postfix properly", expectedPostfixExpression, actual);
    }

    @Test
    public void preceding_returns_1_if_plus_or_minus() throws Exception {
        int expected = 1;
        char input = '+';
        char secondInput = '-';

        int actual = Whitebox.invokeMethod(ExpressionParser.class, "preceding", input);
        int secondActual = Whitebox.invokeMethod(ExpressionParser.class, "preceding", secondInput);

        assertEquals("The result was not correct for priority with +", expected, actual);
        assertEquals("The result was not correct for priority with -", expected, secondActual);
    }

    @Test
    public void preceding_returns_2_if_division_or_multiplication() throws Exception {
        int expected = 2;
        char input = '/';
        char secondInput = '*';

        int actual = Whitebox.invokeMethod(ExpressionParser.class, "preceding", input);
        int secondActual = Whitebox.invokeMethod(ExpressionParser.class, "preceding", secondInput);

        assertEquals("The result was not correct for priority with /", expected, actual);
        assertEquals("The result was not correct for priority with *", expected, secondActual);
    }

    @Test
    public void preceding_returns_3_if_power() throws Exception {
        int expected = 3;
        char input = '^';

        int actual = Whitebox.invokeMethod(ExpressionParser.class, "preceding", input);

        assertEquals("The result was not correct for priority with ^", expected, actual);
    }

    @Test
    public void preceding_returns_minus1_if_symbol_is_not_mathematical() throws Exception {
        int expected = -1;
        char input = '&';

        int actual = Whitebox.invokeMethod(ExpressionParser.class, "preceding", input);

        assertEquals("The result was not correct for priority with &", expected, actual);
    }
}
