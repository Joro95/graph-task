package com.graph.parser;

import com.graph.model.Node;
import com.graph.model.NumberNode;

public class ExpressionParser implements ExpressionParserInterface{

    public Node parseExpression(String expression) {
        return new NumberNode(5);
    }

}
