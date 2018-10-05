package com.graph.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.reflect.Whitebox;

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
        String expected = "+3-5";
        String inputString = "---+-3++++-++++5";
        String actual = InputParser.replaceConsecutivePlusMinus(inputString);

        assertEquals("The plus and minus signs are not replaced correctly", expected, actual);
    }

    @Test
    public void getExpressionReturnsCorrectExpressionWithoutEqualsSign() {
        String expected = "5+10";
        String inputString = "A3=5 + 10";
        String actual = getExpression(inputString);

        assertEquals("The parsed string did not remove the equals sign correctly", expected, actual);
    }

    @Test
    public void findEvenOrOddMinusesReturnsMinusWhenThereAreOddNumberOfMinuses() throws Exception {
        String expected = "-";
        String inputString = "-+--+";
        String actual = org.powermock.reflect.Whitebox.invokeMethod(InputParser.class, "findEvenOrOddMinuses", inputString);

        assertEquals("The expected result is not a Minus", expected, actual);
    }

    @Test
    public void findEvenOrOddMinusesReturnsPlusWhenThereAreEvenNumberOfMinuses() throws Exception {
        String expected = "+";
        String inputString = "-+--+-";
        String actual = org.powermock.reflect.Whitebox.invokeMethod(InputParser.class, "findEvenOrOddMinuses", inputString);

        assertEquals("The expected result is not a Minus", expected, actual);
    }

    @Test
    public void createPlusAndMinusSubstringReturnsSubstringOfPlussesAndMinusesFromExpression() throws Exception {
        String expected = "+--+-";
        String inputString = "+--+-87";
        String actual = org.powermock.reflect.Whitebox.invokeMethod(InputParser.class, "createPlusAndMinusSubstring", inputString);

        assertEquals("The expected result is not the correct substring", expected, actual);
    }

    @Test
    public void isPlusOrMinusReturnsTrueWhenSymbolIsAPlusOrMinus() throws Exception {
        boolean expected = true;
        char inputPlus = '+';
        char inputMinus = '-';
        boolean actual = org.powermock.reflect.Whitebox.invokeMethod(InputParser.class, "isPlusOrMinus", inputPlus);
        boolean actual2 = org.powermock.reflect.Whitebox.invokeMethod(InputParser.class, "isPlusOrMinus", inputMinus);

        assertEquals("The result was false with input +", expected, actual);
        assertEquals("The result was false with input -", expected, actual2);
    }

    @Test
    public void refactorNegativeNumbersAndReferencesCorrectsCodeInAFormatParsableToExpressionTree() throws Exception {
        String expected = "(0-3)/(0-10)*3";
        String inputString = "-3/-10*3";
        String actual = org.powermock.reflect.Whitebox.invokeMethod(InputParser.class,"refactorNegativeNumbersAndReferences", inputString);

        assertEquals("The parsed string is not correctly formed", expected, actual);
    }

    @Test
    public void getExpressionReturnsRefactoredExpressionWithoutTheDeclaration() throws Exception {
        String expected = "3+2";
        String inputString = "A3=3+2";
        String actual = Whitebox.invokeMethod(InputParser.class, "getExpression", inputString);

        assertEquals("The received expression is not properly refactored", expected, actual);
    }
}
