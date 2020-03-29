package com.ikemole.expressionevaluator.structure.node;

import java.util.List;

/**
 * An expression node that represents an inner expression surrounded by brackets.
 */
public class BracketNode extends ExpressionNode{
    private String expression;
    private List<String> innerSteps;
    public BracketNode(String expression){
        super(ExpressionNodeType.Bracket);
        this.expression = expression;
    }

    public String expression() {
        return expression;
    }

    @Override
    public String toString() {
        return String.format("(%s)", expression);
    }

    public List<String> getInnerSteps() {
        return innerSteps;
    }

    public void setInnerSteps(List<String> innerSteps) {
        this.innerSteps = innerSteps;
    }
}
