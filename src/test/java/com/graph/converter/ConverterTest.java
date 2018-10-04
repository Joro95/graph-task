package com.graph.converter;

import com.graph.exception.InvalidInputException;
import org.junit.Test;

import java.util.Map;

import static com.graph.converter.Converter.getColumnNumber;
import static com.graph.converter.Converter.squareNameToColumnRow;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConverterTest {

    //******* SQUARE NAME TO COLUMN AND ROW TESTS **************

    @Test
    public void squareNameToColumnRowExpectMapThatContainsBoth() throws InvalidInputException {
        //Act
        Map<String, Integer> result = squareNameToColumnRow("b3");

        //Assert
        assertTrue(result.containsKey("column"));
        assertTrue(result.containsKey("row"));
        assertEquals(1, result.get("column").intValue());
        assertEquals(2, result.get("row").intValue());
    }

    @Test(expected = InvalidInputException.class)
    public void squareNameToColumnRowThrowsExceptionOnWrongName() throws InvalidInputException {
        squareNameToColumnRow("b33a");
    }

    //******* GET COLUMN NUMBER TESTS **************

    @Test
    public void getColumnNumberWithOneLetter(){
        //Act
        int result = getColumnNumber("E");

        //Assert
        assertEquals(4, result);
    }

    @Test
    public void getColumnNumberWithTwoLetters(){
        //Act
        int result = getColumnNumber("CD");

        //Assert
        assertEquals(81, result);
    }

}
