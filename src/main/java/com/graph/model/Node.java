package com.graph.model;

import com.graph.exception.CellNotInitializedException;
import com.graph.exception.ParseException;

public abstract class Node {

    protected abstract double calculateValue() throws ParseException, CellNotInitializedException;

}
