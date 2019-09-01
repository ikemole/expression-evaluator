package com.ikemole.expressionevaluator.structure.node;

/**
 * This class represents a node in the expression linked list.
 * Each node can either be a number, an operator or an inner expression (inside brackets).
 * Each subtype of this class gives more details on the node type.
 */
public abstract class ExpressionNode {
    private ExpressionNodeType type;
    private ExpressionNode left;
    private ExpressionNode right;

    public ExpressionNode(ExpressionNodeType type){
        this.type = type;
    }

    public ExpressionNodeType type() {
        return type;
    }

    public ExpressionNode left() {
        return left;
    }

    public ExpressionNode right() {
        return right;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
    }
}



