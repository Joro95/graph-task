package com.graph.benchmark;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.WriterConsumer;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.h2.H2Consumer;
import com.graph.exception.CellNotInitializedException;
import com.graph.exception.CircularDependenciesException;
import com.graph.exception.InvalidInputException;
import com.graph.exception.ParseException;
import com.graph.facade.GraphFacade;
import com.graph.finder.SquareFinder;
import com.graph.model.Graph;
import com.graph.model.Square;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@AxisRange(min = 0, max = 0.5)
@BenchmarkMethodChart(filePrefix = "benchmark-lists")
@BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 10)
public class BenchmarkTest {

    private static final WriterConsumer consoleConsumer = new WriterConsumer();
    private static final H2Consumer h2Consumer = new H2Consumer(
            new File("target/charts/foo-db"),
            new File("target/charts"),
            "custom-build-key");

    @Rule
    public BenchmarkRule benchmarkRun = new BenchmarkRule(consoleConsumer, h2Consumer);

    private GraphFacade graphFacade = GraphFacade.getInstance();

    @Before
    public void setup(){
//        clearGraph(graphFacade.getGraph());
//        TestDataGenerator.generateData(graphFacade, 10);
    }

    private void clearGraph(Graph graph){
        List<ArrayList<Square>> table = graph.getTableOfSquares();
        for (ArrayList<Square> columns : table) {
            for (int square = 0; square < columns.size(); square++) {
                columns.set(square, new Square("", Square.Status.NOT_INITIALIZED));
            }
        }
    }

    @Test
    public void benchmarkTestStrategy1() throws CircularDependenciesException, InterruptedException, ParseException, InvalidInputException, ExecutionException, CellNotInitializedException {
        //Arrange
        String square = TestDataGenerator.generateData(graphFacade, 120);

        //Act
//        System.out.println(square);
        graphFacade.processExpression(square + "=1");
        Square square1 = SquareFinder.getSquare("E1", graphFacade.getGraph());
//        System.out.println(square1.getValue());
    }

}
