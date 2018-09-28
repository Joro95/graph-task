package com.graph.finder;

import com.graph.exception.InvalidInputException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SquareFinderUtilsTest {

    private SquareFinderUtils squareFinderUtils = new SquareFinderUtils();

    //******* VALIDATION TESTS **************

    @Test
    public void validateInputPassesWithCorrectData() throws InvalidInputException {
        squareFinderUtils.validateInput("AA", "151");
    }

    @Test(expected = InvalidInputException.class)
    public void validateInputThrowsExceptionGivenColumnIsEmpty() throws InvalidInputException {
        squareFinderUtils.validateInput("", "11");
    }

    @Test(expected = InvalidInputException.class)
    public void validateInputThrowsExceptionGivenRowIsEmpty() throws InvalidInputException {
        squareFinderUtils.validateInput("A", "");
    }

    @Test(expected = InvalidInputException.class)
    public void validateInputThrowsExceptionGivenColumnContainsNonLetter() throws InvalidInputException {
        squareFinderUtils.validateInput("A}", "11");
    }

    @Test(expected = InvalidInputException.class)
    public void validateInputThrowsExceptionGivenColumnTooLarge() throws InvalidInputException {
        squareFinderUtils.validateInput("AAA", "11");
    }

    @Test(expected = InvalidInputException.class)
    public void validateInputThrowsExceptionGivenRowContainsNonNumeric() throws InvalidInputException {
        squareFinderUtils.validateInput("AA", "1A1");
    }

    @Test(expected = InvalidInputException.class)
    public void validateInputThrowsExceptionGivenRowTooLarge() throws InvalidInputException {
        squareFinderUtils.validateInput("AAA", "1111");
    }


    //******* GET COLUMN NUMBER TESTS **************

    @Test
    public void getFirstDigitOccurrenceReturnsExpectedNumber(){
        //Act
        int result = squareFinderUtils.getFirstDigitOccurrence("str2z2yu5");

        //Assert
        assertEquals(3, result);
    }


    //******* GET COLUMN NUMBER TESTS **************

    @Test
    public void getColumnNumberWithOneLetter(){
        //Act
        int result = squareFinderUtils.getColumnNumber("E");

        //Assert
        assertEquals(4, result);
    }

    @Test
    public void getColumnNumberWithTwoLetters(){
        //Act
        int result = squareFinderUtils.getColumnNumber("CD");

        //Assert
        assertEquals(81, result);
    }

}
