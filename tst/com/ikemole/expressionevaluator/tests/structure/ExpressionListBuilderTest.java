package com.ikemole.expressionevaluator.tests.structure;

import com.ikemole.expressionevaluator.exception.BadExpressionException;
import com.ikemole.expressionevaluator.structure.*;
import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.NumberNode;
import com.ikemole.expressionevaluator.structure.node.OperatorNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;
import com.ikemole.expressionevaluator.tests.assertions.ExpressionListAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ExpressionListBuilderTest {
    private ExpressionListBuilder builder = new ExpressionListBuilder();

    @Test
    public void buildExpressionList_simpleExpression(){
        String expression = "2*3";
        ExpressionNode[] expectedResult = {
                new NumberNode(2),
                new OperatorNode(ExpressionNodeType.Multiplication),
                new NumberNode(3)
        };
        assertExpressionTree(expression, expectedResult);
    }

    @Test
    public void buildExpressionList_expressionWithSpaces(){
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
    public void buildExpressionList_expressionWithBrackets(){
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
    public void buildExpressionList_incompleteBrackets_throwsException(){
        BadExpressionException exceptionThrown = assertThrows(BadExpressionException.class, () -> {
            String expression = "12+9-(54^8/(67-43)-29";
            builder.build(expression);
        });
        assertEquals(exceptionThrown.getMessage(), "The open bracket at index 5 was not closed.");
    }

    @Test
    public void buildExpressionList_expressionWithNestedBrackets(){
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
    public void buildExpressionList_expressionStartsWithMinus(){
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
        try{
            ExpressionList expressionList = builder.build(expression);
            ExpressionListAssert.assertListContents(expressionList, expectedResults);
        } catch (BadExpressionException e) {
            e.printStackTrace();
            Assertions.fail(e);
        }
    }
}
