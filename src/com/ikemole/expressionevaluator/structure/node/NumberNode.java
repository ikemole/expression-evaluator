package com.ikemole.expressionevaluator.structure.node;

import java.text.DecimalFormat;

/**
 * This class represents a node in the expression linked list which just contains a number.
 */
public class NumberNode extends ExpressionNode {
    private double number;
    private static DecimalFormat DECIMAL_FORMAT_2DP = new DecimalFormat("#.##");

    public NumberNode(double number){
        super(ExpressionNodeType.Number);
        this.number = number;
    }

    public double number() {
        return number;
    }

    @Override
    public String toString() {
        if(Double.isInfinite(number) || Double.isNaN(number))
            return Double.toString(number);

        if(isWholeNumber())
            return Integer.toString((int) number);

        return DECIMAL_FORMAT_2DP.format(number);
    }

    private boolean isWholeNumber(){
        return number == Math.rint(number);
    }
}
