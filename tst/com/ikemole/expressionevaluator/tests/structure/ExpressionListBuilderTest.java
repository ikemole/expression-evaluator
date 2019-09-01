package com.ikemole.expressionevaluator.tests.structure;

import com.ikemole.expressionevaluator.structure.*;
import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.NumberNode;
import com.ikemole.expressionevaluator.structure.node.OperatorNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;
import com.ikemole.expressionevaluator.tests.assertions.ExpressionListAssert;
import org.junit.jupiter.api.Test;


public class ExpressionListBuilderTest {
    private ExpressionListBuilder builder = new ExpressionListBuilder();

    @Test
    public void test1(){
        String expression = "2*3";
        ExpressionNode[] expectedResult = {
                new NumberNode(2),
                new OperatorNode(ExpressionNodeType.Multiplication),
                new NumberNode(3)
        };
        assertExpressionTree(expression, expectedResult);
    }

    @Test
    public void test_with_spaces(){
        String expression = "12 * 3 + 5";
        ExpressionNode[] expectedResult = {
                new NumberNode(12),
                new OperatorNode(ExpressionNodeType.Multiplication),
                new NumberNode(3),
                new OperatorNode(ExpressionNodeType.Addition),
                new NumberNode(5)
        };
        assertExpressionTree(expression, expectedResult);
    }

    @Test
    public void test_with_brackets(){
        String expression = "12+(4^2)-29";
        ExpressionNode[] expectedResults = {
                new NumberNode(12),
                new OperatorNode(ExpressionNodeType.Addition),
                new BracketNode("4^2"),
                new OperatorNode(ExpressionNodeType.Subtraction),
                new NumberNode(29)
        };
        assertExpressionTree(expression, expectedResults);
    }

    @Test
    public void test_nested_brackets(){
        String expression = "403 + (4 ^ (2 + 1)) - 29 ^ (9 - 7)";
        ExpressionNode[] expectedResults = {
                new NumberNode(403),
                new OperatorNode(ExpressionNodeType.Addition),
                new BracketNode("4 ^ (2 + 1)"),
                new OperatorNode(ExpressionNodeType.Subtraction),
                new NumberNode(29),
                new OperatorNode(ExpressionNodeType.Exponent),
                new BracketNode("9 - 7")
        };
        assertExpressionTree(expression, expectedResults);
    }

    @Test
    public void test_start_with_minus(){
        String expression = "-1 + 2^7";
        ExpressionNode[] expectedResult = {
                new NumberNode(0),
                new OperatorNode(ExpressionNodeType.Subtraction),
                new NumberNode(1),
                new OperatorNode(ExpressionNodeType.Addition),
                new NumberNode(2),
                new OperatorNode(ExpressionNodeType.Exponent),
                new NumberNode(7)
        };
        assertExpressionTree(expression, expectedResult);
    }

    private void assertExpressionTree(String expression, ExpressionNode[] expectedResults){
        ExpressionList expressionList = builder.buildExpressionList(expression);

        ExpressionListAssert.assertListContents(expressionList, expectedResults);
    }
}
