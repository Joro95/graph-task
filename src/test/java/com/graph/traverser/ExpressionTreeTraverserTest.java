package com.graph.traverser;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.InvalidInputException;
import com.graph.exception.ParseException;
import com.graph.facade.GraphFacade;
import com.graph.finder.SquareFinder;
import com.graph.model.Square;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class ExpressionTreeTraverserTest {

    @Test
    public void test() throws CircularDependenciesException, InterruptedException, ParseException, InvalidInputException, ExecutionException, CellNotInitializedException {
        GraphFacade graphFacade = GraphFacade.getInstance();
        graphFacade.processExpression("A2=5");
        graphFacade.processExpression("A1=A2-6+4*3");
        Square square = SquareFinder.getSquare("A1", graphFacade.getGraph());

        System.out.println(square.getValue());
    }

}
