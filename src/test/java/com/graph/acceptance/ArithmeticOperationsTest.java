package com.graph.acceptance;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.InvalidInputException;
import com.graph.exception.ParseException;
import com.graph.facade.GraphFacade;
import com.graph.finder.SquareFinder;
import com.graph.model.Square;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * Created by developer on 09/10/18.
 */
public class ArithmeticOperationsTest {

    @Test
    public void allTheMainArithmeticOperationsAreSupportedAndCalculatedCorrectly() throws CircularDependenciesException, InterruptedException, ParseException, InvalidInputException, ExecutionException, CellNotInitializedException {
        String input = "A1=(5+((2-5)/3)^4)*10";
        double expectedOutput = 60.0;

        GraphFacade graphFacade = GraphFacade.getInstance();
        graphFacade.processExpression(input);
        Square square = SquareFinder.getSquare("A1", graphFacade.getGraph());

        assertEquals(expectedOutput, square.getValue(), 0.001);
    }
}
