package com.ikemole.expressionevaluator;

import com.ikemole.expressionevaluator.exception.BadExpressionException;
import com.ikemole.expressionevaluator.structure.ExpressionChain;
import com.ikemole.expressionevaluator.structure.ExpressionChainBuilder;
import com.ikemole.expressionevaluator.structure.ExpressionResultWithSteps;
import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.NumberNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;

/**
 * A class used to evaluate a math expression.
 */
public class ExpressionEvaluator {
    private ExpressionChainBuilder expressionChainBuilder = new ExpressionChainBuilder();

    /**
     * Evaluate a math expression and return the result.
     * Example: "2+3*4" should return 14
     * @param expression A string containing an expression to be evaluated.
     * @return The result of solving the expression
     */
    public double evaluate(String expression) throws BadExpressionException {
        ExpressionResultWithSteps resultWithSteps = evaluateAndShowWorking(expression, false);
        return resultWithSteps.getResult();
    }

    /**
     * Evaluate a math expression and return the result along with the steps taken to solve it.
     * @param expression A string containing an expression to be evaluated.
     * @param showWorking Indicate whether to record the steps of solving the equation
     * @return The result of solving the expression
     */
    public ExpressionResultWithSteps evaluateAndShowWorking(String expression, boolean showWorking)
            throws BadExpressionException
    {
        ExpressionChain expressionChain = expressionChainBuilder.build(expression);

        if(showWorking)
            expressionChain.recordCurrentStep();

        while (expressionChain.hasOperators()){
            ExpressionNode nodeToProcess = expressionChain.getHighestPriorityNode();
            double nodeResult = evaluateNode(nodeToProcess, showWorking);

            if(showWorking && nodeToProcess.type() == ExpressionNodeType.Bracket)
                expressionChain.recordInnerStepsForBracket((BracketNode) nodeToProcess);

            ExpressionNode resultNode = new NumberNode(nodeResult);
            expressionChain.replace(nodeToProcess, resultNode);

            if(showWorking)
                expressionChain.recordCurrentStep();
        }

        double nodeResult = ((NumberNode) expressionChain.first()).number();
        return new ExpressionResultWithSteps(nodeResult, expressionChain.getSteps());
    }

    /**
     * Evaluate the expression at this node. The node here is usually an operator node in which case
     * we apply the operator to the values on its left and right. Alternatively, it could be a
     * bracket node in which case the inner expression is evaluated.
     */
    private double evaluateNode(ExpressionNode node, boolean showWorking) throws BadExpressionException {
        if(node.type() == ExpressionNodeType.Number)
            return ((NumberNode)node).number();

        if(node.type() == ExpressionNodeType.Bracket){
            BracketNode bracketNode = ((BracketNode)node);
            String innerExpression = bracketNode.expression();
            ExpressionResultWithSteps resultWithSteps = evaluateAndShowWorking(innerExpression, showWorking);

            if(showWorking)
                bracketNode.setInnerSteps(resultWithSteps.getSteps());

            return resultWithSteps.getResult();
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
