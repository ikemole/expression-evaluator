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

public class ExpressionListTest {
    private ExpressionListBuilder treeBuilder = new ExpressionListBuilder();

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
    public void replace_oldNodeHasBrackets(){
        String expression = "12+(4^2)-29";
        ExpressionList expressionList = treeBuilder.buildExpressionList(expression);
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
    public void replace_oldNodeIsOperator(){
        String expression = "12+9-29";
        ExpressionList expressionList = treeBuilder.buildExpressionList(expression);
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
        ExpressionList expressionList = treeBuilder.buildExpressionList(expression);
        ExpressionNode highestPriorityNode = expressionList.getHighestPriorityNode();
        ExpressionNodeAssert.assertEquals(expectedNode, highestPriorityNode);
    }

}
