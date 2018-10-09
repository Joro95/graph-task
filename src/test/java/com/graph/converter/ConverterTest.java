package com.graph.converter;

import com.graph.exception.InvalidInputException;
import org.junit.Test;

import java.util.Map;

import static com.graph.converter.Converter.columnToLetters;
import static com.graph.converter.Converter.rowColumnToName;
import static com.graph.converter.Converter.squareNameToColumnRow;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConverterTest {

    //******* SQUARE NAME TO COLUMN AND ROW TESTS **************

    @Test
    public void squareNameToColumnRowWithTwoSymbols() throws InvalidInputException {
        //Act
        Map<String, Integer> result = squareNameToColumnRow("b3");

        //Assert
        assertTrue(result.containsKey("column"));
        assertTrue(result.containsKey("row"));
        assertEquals(1, result.get("column").intValue());
        assertEquals(2, result.get("row").intValue());
    }

    @Test
    public void squareNameToColumnRowWithFourSymbols() throws InvalidInputException {
        //Act
        Map<String, Integer> result = squareNameToColumnRow("AA53");

        //Assert
        assertTrue(result.containsKey("column"));
        assertTrue(result.containsKey("row"));
        assertEquals(26, result.get("column").intValue());
        assertEquals(52, result.get("row").intValue());
    }

    @Test(expected = InvalidInputException.class)
    public void squareNameToColumnRowThrowsExceptionOnWrongName() throws InvalidInputException {
        squareNameToColumnRow("b33a");
    }

    //******* COLUMN TO LETTERS TESTS **************

    @Test
    public void columnToLettersLesserThan26(){
        //Act
        String result = columnToLetters(3);

        //Assert
        assertEquals("D", result);
    }

    @Test
    public void columnToLettersGreaterThan26(){
        //Act
        String result = columnToLetters(30);

        //Assert
        assertEquals("AE", result);
    }

    //******* ROW AND COLUMN TO NAME TESTS **************

    @Test
    public void rowColumnToNameExpectOneLetterTest(){
        //Act
        String result = rowColumnToName(3, 3);

        //Assert
        assertEquals("D3", result);
    }

    @Test
    public void rowColumnToNameExpectTwoLettersTest(){
        //Act
        String result = rowColumnToName(3, 30);

        //Assert
        assertEquals("AE3", result);
    }
}
