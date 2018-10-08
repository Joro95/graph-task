package com.graph.validator;

import com.graph.exception.InvalidInputException;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;

public class ValidatorTest {
    
    //******* VALIDATE SQUARE NAME TESTS **************

    @Test
    public void validateSquareNamePassesWithCorrectData() throws InvalidInputException {
        Validator.validateSquareName("151", "A");
    }

    @Test(expected = InvalidInputException.class)
    public void validateSquareNameThrowsExceptionGivenColumnIsEmpty() throws InvalidInputException {
        Validator.validateSquareName("", "11");
    }

    @Test(expected = InvalidInputException.class)
    public void validateSquareNameThrowsExceptionGivenRowIsEmpty() throws InvalidInputException {
        Validator.validateSquareName("A", "");
    }

    @Test(expected = InvalidInputException.class)
    public void validateSquareNameThrowsExceptionGivenColumnContainsNonLetter() throws InvalidInputException {
        Validator.validateSquareName("A}", "11");
    }

    @Test(expected = InvalidInputException.class)
    public void validateSquareNameThrowsExceptionGivenColumnTooLarge() throws InvalidInputException {
        Validator.validateSquareName("AAA", "11");
    }

    @Test(expected = InvalidInputException.class)
    public void validateSquareNameThrowsExceptionGivenRowContainsNonNumeric() throws InvalidInputException {
        Validator.validateSquareName("AA", "1A1");
    }

    @Test(expected = InvalidInputException.class)
    public void validateSquareNameThrowsExceptionGivenRowTooLarge() throws InvalidInputException {
        Validator.validateSquareName("AAA", "1111");
    }

    //******* CHECK FOR EQUAL SIGN TESTS **************

    @Test(expected = InvalidInputException.class)
    public void checkForEqualsSignThrowsException() throws Exception {
        Whitebox.invokeMethod(Validator.class, "checkForEqualsSign","asd");
    }

    @Test
    public void checkForEqualsSignDoesNotThrowException() throws Exception {
        Whitebox.invokeMethod(Validator.class, "checkForEqualsSign","asd=12");
    }

    @Test
    public void checkBracketPairsValidatesIfAllBracketsAreClosed() throws Exception {
        String expected = "(2*23)";
        String inputString = "(2*23)";
        String actual = Whitebox.invokeMethod(Validator.class, "checkBracketPairs", inputString);

        assertEquals("The bracket check did not pass correctly", expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void checkBracketPairsThrowsExceptionIfThereIsAnUnclosedBracket() throws Exception {
        Whitebox.invokeMethod(Validator.class, "checkBracketPairs", "(()*23");
    }

    @Test(expected = InvalidInputException.class)
    public void checkConsecutiveSymbolsThrowsExceptionWhenThereAreConsecutiveSymbolsForMultiplicationDivisionPower() throws Exception {
        String inputString = "**/ ";
        Whitebox.invokeMethod(Validator.class, "checkConsecutiveSymbols", inputString);
    }

    @Test(expected = InvalidInputException.class)
    public void checkForIllegalSignsThrowsExceptionWhenInputDataContainsIllegalSigns() throws Exception {
        String inputString = "&54*7";
        Whitebox.invokeMethod(Validator.class, "checkForIllegalSigns", inputString);
    }

}
