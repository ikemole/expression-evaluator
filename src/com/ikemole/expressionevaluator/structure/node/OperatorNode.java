package com.ikemole.expressionevaluator.structure.node;

/**
 * This class represents a node in the expression linked list which is an operator.
 */
public class OperatorNode extends ExpressionNode{
    public OperatorNode(ExpressionNodeType type){
        super(type);
    }
}