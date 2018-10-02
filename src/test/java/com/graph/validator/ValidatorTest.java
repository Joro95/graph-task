package com.graph.validator;

import com.graph.exception.InvalidInputException;
import org.junit.Test;

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
    public void checkForEqualsSignThrowsException() throws InvalidInputException {
        Validator.checkForEqualsSign("asd");
    }

    @Test
    public void checkForEqualsSignDoesntThrowException() throws InvalidInputException {
        Validator.checkForEqualsSign("asd=12");
    }

}
