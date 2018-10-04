package com.graph.facade;

import com.graph.model.Graph;
import com.graph.model.Square;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.graph.converter.Converter.columnToLetters;

public class GraphPrinter {

    public static final int ROWS = 10;
    public static final int COLUMNS = 10;

    public void printGraph(Graph graph){

        for (int row = 0; row < (ROWS * 2 + 2); row++){
            for (int column = 0; column < (COLUMNS * 2 + 2); column++){
                printTopLeftEmptySquare(row, column);
                columnLogic(row, column, graph);
            }
            System.out.println();

        }

    }

    private void columnLogic(int row, int column, Graph graph) {
        if (row == 0 && column > 0){
            printColumnNames(column);
        }
        if (row > 0) {
            if (row % 2 == 1) {
                printUnderlineSeparators(column);
            }
            if (row % 2 == 0) {
                if (column > 0){
                    printSquares(row, column, graph);
                } else {
                    printRows(row);
                }
            }
        }
    }

    private void printSquares(int row, int column, Graph graph) {
        if (column % 2 == 0){
            Square square = graph.getGraph().get(row / 2 - 1).get(column / 2 - 1);
            switch (square.getStatus()){
                case INITIALIZED:
                    printSquareValue(square);
                    break;
                case NOT_INITIALIZED:
                    System.out.print(" N/I  ");
                    break;
                case ERROR:
                    System.out.print("ERROR ");
                    break;
                default:
                    System.out.print("      ");
            }
        } else {
            System.out.print("|");
        }
    }

    private void printSquareValue(Square square) {
        double value = square.getValue();
        if (value >= 10000){
            System.out.print(" OOB  ");
            return;
        }
        double roundedValue = round(value);
        String valueString = Double.toString(roundedValue);
        int emptySpaces = 6 - valueString.length();
        for (int i = emptySpaces; i > 0; i--){
            System.out.print(" ");
        }
        System.out.print(valueString);

    }

    private void printUnderlineSeparators(int column) {
        if (column % 2 == 0){
            if (column == 0){
                System.out.print("---");
            } else {
                System.out.print("--");
            }
        } else {
            System.out.print("--|--");
        }
    }

    private void printColumnNames(int column) {
        if (column % 2 == 0){
            if (columnToLetters(column / 2 - 1).length() == 1){
                System.out.print(" ");
            }
            System.out.print(columnToLetters(column / 2 - 1));
        } else {
            System.out.print("  |  ");
        }
    }

    private void printRows(int row) {
        row = row / 2;
        int rowDigitSize = String.valueOf(row).length();
        if(rowDigitSize == 1) {
            System.out.print("  ");
        } else if (rowDigitSize == 2){
            System.out.print(" ");
        }
        System.out.print(row + "  ");
    }


    private void printTopLeftEmptySquare(int row, int column) {
        if (row == 0 && column == 0){
            System.out.print("   ");
        }
    }

    private double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
