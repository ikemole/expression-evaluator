package com.ikemole.expressionevaluator.structure.node;

/**
 * This class represents a node in the expression linked list which just contains a number.
 */
public class NumberNode extends ExpressionNode {
    private double number;

    public NumberNode(double number){
        super(ExpressionNodeType.Number);
        this.number = number;
    }

    public double number() {
        return number;
    }
}
