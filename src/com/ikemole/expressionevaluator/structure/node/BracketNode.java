package com.ikemole.expressionevaluator.structure.node;

/**
 * An expression node that represents an inner expression surrounded by brackets.
 */
public class BracketNode extends ExpressionNode{
    private String expression;
    public BracketNode(String expression){
        super(ExpressionNodeType.Bracket);
        this.expression = expression;
    }

    public String expression() {
        return expression;
    }
}
