package com.ikemole.expressionevaluator.tests.structure;

import com.ikemole.expressionevaluator.exception.BadExpressionException;
import com.ikemole.expressionevaluator.structure.*;
import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.NumberNode;
import com.ikemole.expressionevaluator.structure.node.OperatorNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;
import com.ikemole.expressionevaluator.tests.assertions.ExpressionChainAssert;
import com.ikemole.expressionevaluator.tests.assertions.ExpressionNodeAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionChainTest {
    private ExpressionChainBuilder expressionChainBuilder = new ExpressionChainBuilder();

    @Test
    public void highestPriority_oneOperator(){
        String expression = "3*54";
        ExpressionNode expectedNode = new OperatorNode(ExpressionNodeType.Multiplication);
        assertHighestPriorityNode(expression, expectedNode);
    }

    @Test
    public void highestPriority_twoOperators(){
        String expression = "3*5^4";
        ExpressionNode expectedNode = new OperatorNode(ExpressionNodeType.Exponent);
        assertHighestPriorityNode(expression, expectedNode);
    }

    @Test
    public void highestPriority_withBrackets(){
        String expression = "12+(4^2)-29";
        ExpressionNode expectedNode = new BracketNode("4^2");
        assertHighestPriorityNode(expression, expectedNode);
    }

    @Test
    public void highestPriority_verifyOperatorOrder(){
        String expression = "24/12+(4-2)-29^7*2";
        ExpressionNodeType[] expectedNodeTypeOrder = {
                ExpressionNodeType.Bracket,
                ExpressionNodeType.Exponent,
                ExpressionNodeType.Division,
                ExpressionNodeType.Multiplication,
                ExpressionNodeType.Subtraction,
                ExpressionNodeType.Addition,
        };
        ExpressionChain expressionChain = null;
        try {
            expressionChain = expressionChainBuilder.build(expression);
        } catch (BadExpressionException e) {
            fail(e);
        }

        for (ExpressionNodeType expectedType : expectedNodeTypeOrder){
            ExpressionNodeType actualType = expressionChain.getHighestPriorityNode().type();
            assertEquals(expectedType, actualType);
        }

        assertFalse(expressionChain.hasOperators());
    }

    @Test
    public void replace_whenOldNodeIsBracketNode(){
        String expression = "12+(4^2)-29";
        ExpressionChain expressionChain = null;
        try {
            expressionChain = expressionChainBuilder.build(expression);
        } catch (BadExpressionException e) {
            fail(e);
        }
        ExpressionNode bracketNode = expressionChain.first().right().right();
        ExpressionNode newNode = new NumberNode(100);
        expressionChain.replace(bracketNode, newNode);
        ExpressionNode[] expectedResult = {
                new NumberNode(12),
                new OperatorNode(ExpressionNodeType.Addition),
                newNode,
                new OperatorNode(ExpressionNodeType.Subtraction),
                new NumberNode(29)
        };
        ExpressionChainAssert.assertNodesAreCorrect(expressionChain, expectedResult);
    }

    @Test
    public void replace_whenOldNodeIsOperatorNode(){
        String expression = "12+9-29";
        ExpressionChain expressionChain = null;
        try {
            expressionChain = expressionChainBuilder.build(expression);
        } catch (BadExpressionException e) {
            fail(e);
        }
        ExpressionNode plusNode = expressionChain.first().right();
        ExpressionNode newNode = new NumberNode(100);
        expressionChain.replace(plusNode, newNode);
        ExpressionNode[] expectedResult = {
                newNode,
                new OperatorNode(ExpressionNodeType.Subtraction),
                new NumberNode(29)
        };
        ExpressionChainAssert.assertNodesAreCorrect(expressionChain, expectedResult);
    }

    @Test
    public void test_toString(){
        assertToString("12+9-29");
    }

    @Test
    public void test_toString_withBracket(){
        assertToString("12+9-(54^8/(67-43)-29)");
    }

    private void assertHighestPriorityNode(String expression, ExpressionNode expectedNode) {
        try {
            ExpressionChain expressionChain = expressionChainBuilder.build(expression);
            ExpressionNode highestPriorityNode = expressionChain.getHighestPriorityNode();
            ExpressionNodeAssert.assertEquals(expectedNode, highestPriorityNode);
        } catch (BadExpressionException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    private void assertToString(String expression) {
        try {
            ExpressionChain expressionChain = expressionChainBuilder.build(expression);
            String actualToString = expressionChain.toString();
            Assertions.assertEquals(expression, actualToString);
        } catch (BadExpressionException e) {
            fail(e);
        }
    }

}
