package com.ikemole.expressionevaluator.structure.node;

/**
 * This represents the type of a node in the expression linked list.
 * Each type also has a number that indicates its mathematical priority.
 */
public enum ExpressionNodeType {
    Bracket(6),
    Exponent(5, "^"),
    Division(4, "/"),
    Multiplication(3, "*"),
    Subtraction(2, "-"), // note: we place subtraction before addition to handle negative numbers better
    Addition(1, "+"),
    Number(0);

    private int priority;
    private String opStr;

    public int priority() {
        return priority;
    }

    ExpressionNodeType(int priority){
        this.priority = priority;
    }

    ExpressionNodeType(int priority, String opStr){
        this.priority = priority;
        this.opStr = opStr;
    }

    public String operatorStr() {
        return opStr;
    }
}
