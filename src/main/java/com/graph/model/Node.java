package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ParseException;

public interface Node {

    double calculateValue() throws ParseException, CellNotInitializedException;
}
