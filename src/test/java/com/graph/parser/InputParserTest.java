package com.graph.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.graph.parser.InputParser.getExpression;
import static org.junit.Assert.assertEquals;

/**
 * Created by developer on 02/10/18.
 */
@RunWith(JUnit4.class)
public class InputParserTest {

    @Test
    public void removeWhitespacesSuccessfullyRemovesUnusedSpaces() {
        String expected = "5+3";
        String inputString = "    5    + 3    ";
        String actual = InputParser.removeWhitespaces(inputString);

        assertEquals("The returned response is not parsed correctly", expected, actual);
    }

    @Test
    public void replaceConsecutivePlusMinusSuccessfullyReplacesMinusesAndPluses() {

    }



    @Test
    public void getExpressionReturnsCorrectExpressionWithoutEqualsSign() {
        String expected = "5+10";
        String inputString = "A3=5 + 10";
        String actual = getExpression(inputString);

        assertEquals("The parsed string did not remove the equals sign correctly", expected, actual);
    }
}
