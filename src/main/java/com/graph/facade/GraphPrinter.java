package com.graph.facade;

import com.graph.model.Graph;

public class GraphPrinter {

    public void printGraph(Graph graph){

        for (int row = 0; row < 42; row++){
            for (int column = 0; column < 42; column++){
                printTopLeftEmptySquare(row, column);
                columnLogic(row, column);

            }
            System.out.println();

        }

    }

    private void columnLogic(int row, int column) {
        if (row == 0 && column > 0){
            printColumnNames(column);
        }
        if (row > 0) {
            if (row % 2 == 1) {
                printUnderlineSeparators(column);
            }
            if (row % 2 == 0) {
                if (column > 0){
                    printSquares(row, column);
                } else {
                    System.out.print(row / 2);
                }
            }
        }
    }

    private void printSquares(int row, int column) {
//        if (column % 2 == 0){
//        } else {
//        }
        System.out.print("a");
    }

    private void printUnderlineSeparators(int column) {
        if (column % 2 == 0){
            String columnToString = String.valueOf(column / 2);
            for (char c : columnToString.toCharArray()){
                System.out.print("-");
            }
        } else {
            System.out.print("--|--");
        }
    }

    private void printColumnNames(int column) {
        if (column % 2 == 0){
            System.out.print(column / 2);
        } else {
            System.out.print("  |  ");
        }
    }

    private void printTopLeftEmptySquare(int row, int column) {
        if (row == 0 && column == 0){
            System.out.print(" ");
        }
    }

    private void printRowNumbers(int x, int y) {
        if (x == 0 && y >= 1){
            if (y % 2 == 0){
                System.out.println((y) / 2);
            } else if ((y % 2 == 1) && y != 1){
                System.out.println("-");
            } else {
                System.out.println("=");
            }
        }
    }

}
