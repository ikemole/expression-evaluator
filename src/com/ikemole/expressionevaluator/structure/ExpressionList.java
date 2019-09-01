package com.ikemole.expressionevaluator.structure;

import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;

/**
 * This class represents an expression in the form of a linked list.
 * Each node is either a number, an operator or a bracket expression.
 * The nodes are doubly-linked to each other. Therefore, for an operator node,
 * the "left" node refers to its left operand while the "right" node is the right operand.
 */
public class ExpressionList {
    private ExpressionNode first;
    private ExpressionNode last;
    private int nodeCount;

    /**
     * Add a new node to the list
     */
    public void add(ExpressionNode node){
        if(first == null){
            first = node;
        } else {
            last.setRight(node);
            node.setLeft(last);
        }

        last = node;
        nodeCount++;
    }

    /**
     * Retrieve the node with the highest priority.
     * The priority is defined by the BODMAS rule.
     */
    public ExpressionNode getHighestPriorityNode() {
        int maxValue = Integer.MIN_VALUE;
        ExpressionNode highestPriorityNode = null;
        ExpressionNode node = first;

        while (node != null){
            if(node.type().value() > maxValue){
                maxValue = node.type().value();
                highestPriorityNode = node;
            }
            node = node.right();
        }

        return highestPriorityNode;
    }

    /**
     * Replace a certain node with another. That is, remove the
     * "nodeToReplace" from the list and put "newNode" in its place.
     * If the node to be replaced is an operator, both the left and
     * right operands are also removed from the list. If it's a bracket
     * node, then only that node is replaced.
     */
    public void replace(ExpressionNode nodeToReplace, ExpressionNode newNode) {
        ExpressionNode newLeft;
        ExpressionNode newRight;

        // replace the nodes involved with the value
        switch (nodeToReplace.type()) {
            case Bracket:
                newLeft = nodeToReplace.left();
                newRight = nodeToReplace.right();
                break;
            case Exponent:
            case Division:
            case Multiplication:
            case Addition:
            case Subtraction:
                newLeft = nodeToReplace.left().left();
                newRight = nodeToReplace.right().right();
                nodeCount = nodeCount - 2; // we removed 3 nodes and added 1
                break;
            default:
                throw new IllegalArgumentException("Unexpected type: " + nodeToReplace.type().name());
        }

        if(newLeft == null){
            first = newNode;
        } else {
            newLeft.setRight(newNode);
            newNode.setLeft(newLeft);
        }

        if(newRight == null){
            last = newNode;
        } else {
            newRight.setLeft(newNode);
            newNode.setRight(newRight);
        }
    }

    public ExpressionNode first() {
        return first;
    }

    public int length() {
        return nodeCount;
    }

    /**
     * Check if the expression is fully solved.
     * It's solved when there is only one node in the list and the node is a number.
     */
    public boolean isSolved(){
        return first != null && first == last && first.type() == ExpressionNodeType.Number;
    }
}
