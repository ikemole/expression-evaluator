package com.ikemole.expressionevaluator.tests.structure;

import com.ikemole.expressionevaluator.structure.*;
import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.NumberNode;
import com.ikemole.expressionevaluator.structure.node.OperatorNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;
import com.ikemole.expressionevaluator.tests.assertions.ExpressionListAssert;
import com.ikemole.expressionevaluator.tests.assertions.ExpressionNodeAssert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpressionListTest {
    private ExpressionListBuilder expressionListBuilder = new ExpressionListBuilder();

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
        ExpressionList expressionList = expressionListBuilder.build(expression);

        for (ExpressionNodeType expectedType : expectedNodeTypeOrder){
            ExpressionNodeType actualType = expressionList.getHighestPriorityNode().type();
            assertEquals(expectedType, actualType);
        }

        assertTrue(expressionList.isSolved());
    }

    @Test
    public void replace_whenOldNodeIsBracketNode(){
        String expression = "12+(4^2)-29";
        ExpressionList expressionList = expressionListBuilder.build(expression);
        ExpressionNode bracketNode = expressionList.first().right().right();
        ExpressionNode newNode = new NumberNode(100);
        expressionList.replace(bracketNode, newNode);
        ExpressionNode[] expectedResult = {
                new NumberNode(12),
                new OperatorNode(ExpressionNodeType.Addition),
                newNode,
                new OperatorNode(ExpressionNodeType.Subtraction),
                new NumberNode(29)
        };
        ExpressionListAssert.assertListContents(expressionList, expectedResult);
    }

    @Test
    public void replace_whenOldNodeIsOperatorNode(){
        String expression = "12+9-29";
        ExpressionList expressionList = expressionListBuilder.build(expression);
        ExpressionNode plusNode = expressionList.first().right();
        ExpressionNode newNode = new NumberNode(100);
        expressionList.replace(plusNode, newNode);
        ExpressionNode[] expectedResult = {
                newNode,
                new OperatorNode(ExpressionNodeType.Subtraction),
                new NumberNode(29)
        };
        ExpressionListAssert.assertListContents(expressionList, expectedResult);
    }

    private void assertHighestPriorityNode(String expression, ExpressionNode expectedNode) {
        ExpressionList expressionList = expressionListBuilder.build(expression);
        ExpressionNode highestPriorityNode = expressionList.getHighestPriorityNode();
        ExpressionNodeAssert.assertEquals(expectedNode, highestPriorityNode);
    }

}
