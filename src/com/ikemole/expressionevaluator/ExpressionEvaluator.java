package com.ikemole.expressionevaluator;

import com.ikemole.expressionevaluator.structure.ExpressionList;
import com.ikemole.expressionevaluator.structure.ExpressionListBuilder;
import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.NumberNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;

/**
 * A class used to evaluate a math expression.
 */
public class ExpressionEvaluator {
    private ExpressionListBuilder expressionListBuilder = new ExpressionListBuilder();

    /**
     * Evaluate a math expression and return the value.
     * Example: "2+3*4" should return 14
     * @param expression A string containing an expression to be evaluated.
     * @return The result of solving the expression
     */
    public double evaluate(String expression){
        ExpressionList expressionList = expressionListBuilder.build(expression);

        /*
        Approach:
        - Find the operator with the highest priority.
        - Evaluate it (using its left and right operands).
        - Replace that sub-expression with the result.
        - Repeat until a single number is left.
         */
        while (!expressionList.isSolved()){
            ExpressionNode nodeToProcess = expressionList.getHighestPriorityNode();
            double nodeResult = evaluateNode(nodeToProcess);

            // If infinite or NaN, no need to continue solving the rest of the expression
            if(Double.isInfinite(nodeResult) || Double.isNaN(nodeResult))
               return nodeResult;

            ExpressionNode resultNode = new NumberNode(nodeResult);
            expressionList.replace(nodeToProcess, resultNode);
        }

        return ((NumberNode) expressionList.first()).number();
    }


    /**
     * Evaluate the expression at this node. The node here is usually an operator node in which case
     * we apply the operator to the values on its left and right. Alternatively, it could be a
     * bracket node in which case the inner expression is evaluated.
     */
    private double evaluateNode(ExpressionNode node) {
        if(node.type() == ExpressionNodeType.Number)
            return ((NumberNode)node).number();

        if(node.type() == ExpressionNodeType.Bracket){
            String innerExpression = ((BracketNode)node).expression();
            return evaluate(innerExpression);
        }

        /*
        The remaining operators require a left and a right value.
         */
        validateOperandNode(node.left());
        validateOperandNode(node.right());

        double leftVal = ((NumberNode)node.left()).number();
        double rightVal = ((NumberNode)node.right()).number();

        switch (node.type()) {
            case Exponent:
                return Math.pow(leftVal, rightVal);
            case Division:
                return leftVal / rightVal;
            case Multiplication:
                return leftVal * rightVal;
            case Addition:
                return leftVal + rightVal;
            case Subtraction:
                return leftVal - rightVal;
            default:
                throw new IllegalArgumentException("Unexpected node type: " + node.type().name());
        }
    }


    /**
     * For operations that require a right and left operands, this validates that each operand
     * is valid and is a number.
     */
    private void validateOperandNode(ExpressionNode node) {
        if(node == null)
            throw new IllegalArgumentException("The operand must not be null");

        if(node.type() != ExpressionNodeType.Number)
            throw new IllegalArgumentException("The operand must be a number");
    }
}
