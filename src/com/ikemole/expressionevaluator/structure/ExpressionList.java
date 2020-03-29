package com.ikemole.expressionevaluator.structure;

import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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
    private PriorityQueue<ExpressionNode> operatorPriorityQueue;
    private List<String> steps;

    public ExpressionList(){
        initializePriorityQueue();
        steps = new ArrayList<>();
    }

    private void initializePriorityQueue() {
        // The priority of an operator is based on the integer value of its ExpressionNodeType enum.
        // That is, a higher number means higher priority (following the BODMAS rule).
        // If they're the same type, the first one in the expression gets higher priority.
        operatorPriorityQueue = new PriorityQueue<>((operator1, operator2) -> {
            if(operator2.type() != operator1.type())
                return operator2.type().priority() - operator1.type().priority();
            else
                return operator1.position() - operator2.position();
        });
    }

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

        node.setPosition(nodeCount);
        addToPriorityQueue(node);
    }

    /**
     * Add the operation to a priority queue as a way
     * to determine the order in which they are processed.
     * Number nodes are not added to the queue.
     */
    private void addToPriorityQueue(ExpressionNode node) {
        if(node.type() != ExpressionNodeType.Number)
            operatorPriorityQueue.add(node);
    }


    /**
     * Retrieve the node with the highest priority.
     */
    public ExpressionNode getHighestPriorityNode() {
        return operatorPriorityQueue.remove();
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

        nodeToReplace.setRight(null);
        nodeToReplace.setLeft(null);
    }

    /**
     * Add the steps that were taken inside bracket node to this expression's steps.
     */
    public void recordInnerStepsForBracket(BracketNode bracketNode) {
        List<String> innerSteps = bracketNode.getInnerSteps();
        if(innerSteps == null || innerSteps.size() == 0)
            return;

        StringBuilder sb = new StringBuilder();
        ExpressionNode node = first;

        while (node != null){
            if(node == bracketNode){
                sb.append("(%s)");
            } else {
                sb.append(node.toString());
            }

            node = node.right();
        }

        String brFormat = sb.toString();
        for (String innerStep : innerSteps) {
            String step = String.format(brFormat, innerStep);
            addStep(step);
        }
    }

    public ExpressionNode first() {
        return first;
    }

    public int length() {
        return nodeCount;
    }

    /**
     * Check if the expression still has any operators left.
     * This can also indicate when the expression has been completely solved.
     */
    public boolean hasOperators(){
        return !operatorPriorityQueue.isEmpty();
    }

    public void recordCurrentStep(){
        addStep(toString());
    }

    private void addStep(String step){
        if(!steps.contains(step))
            steps.add(step);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        ExpressionNode node = first;

        while (node != null){
            sb.append(node.toString());
            node = node.right();
        }

        return sb.toString();
    }

    public List<String> getSteps() {
        return steps;
    }
}
