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
public class DependencyGraphUpdateTest {

    @Test
    public void aCellWithDependenciesUpdatesIfTheirValueChanges() throws CircularDependenciesException, InterruptedException, ParseException, InvalidInputException, ExecutionException, CellNotInitializedException {
        String createAOneExpression = "A1=B1+C2";
        String createBOneExpression = "B1=C2";
        String createCTwoExpression = "C2=2";
        double expectedOutputFromUpdatingCTwoOnBOne = 2;
        double expectedOutputFromUpdatingBOneAndCTwoOnAOne = 4;
        double expectedOutputFromUpdatingCTwoOnBOne2 = 4;
        double expectedOutputFromUpdatingBOneAndCTwoOnAOne2 = 8;

        GraphFacade graphFacade = GraphFacade.getInstance();
        graphFacade.processExpression(createAOneExpression);
        graphFacade.processExpression(createBOneExpression);
        graphFacade.processExpression(createCTwoExpression);
        Square squareA = SquareFinder.getSquare("A1", graphFacade.getGraph());
        Square squareB = SquareFinder.getSquare("B1", graphFacade.getGraph());

        assertEquals(expectedOutputFromUpdatingBOneAndCTwoOnAOne, squareA.getValue(), 0.001);
        assertEquals(expectedOutputFromUpdatingCTwoOnBOne, squareB.getValue(), 0.001);

        String createCTwoExpression2 = "C2=4";
        graphFacade.processExpression(createCTwoExpression2);
        
        assertEquals(expectedOutputFromUpdatingBOneAndCTwoOnAOne2, squareA.getValue(), 0.001);
        assertEquals(expectedOutputFromUpdatingCTwoOnBOne2, squareB.getValue(), 0.001);
    }
}
