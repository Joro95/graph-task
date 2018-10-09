package com.graph.finder;

import com.graph.exception.InvalidInputException;
import com.graph.model.Graph;
import com.graph.model.Square;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SquareFinderTest {

    //******* GET SQUARE TESTS **************

    @Test
    public void getSquare() throws InvalidInputException {
        //Arrange
        Graph graph = new Graph();

        //Act
        Square square = SquareFinder.getSquare("C3", graph);

        //Assert
        assertEquals("C3", square.getName());
        assertEquals(Square.Status.NOT_INITIALIZED, square.getStatus());

    }

}
