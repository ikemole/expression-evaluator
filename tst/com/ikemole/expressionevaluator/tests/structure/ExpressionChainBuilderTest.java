package com.ikemole.expressionevaluator.tests.structure;

import com.ikemole.expressionevaluator.exception.BadExpressionException;
import com.ikemole.expressionevaluator.structure.*;
import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.NumberNode;
import com.ikemole.expressionevaluator.structure.node.OperatorNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;
import com.ikemole.expressionevaluator.tests.assertions.ExpressionChainAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ExpressionChainBuilderTest {
    private ExpressionChainBuilder builder = new ExpressionChainBuilder();

    @Test
    public void buildExpressionChain_simpleExpression(){
        String expression = "2*3";
        ExpressionNode[] expectedResult = {
                new NumberNode(2),
                new OperatorNode(ExpressionNodeType.Multiplication),
                new NumberNode(3)
        };
        assertExpressionChain(expression, expectedResult);
    }

    @Test
    public void buildExpressionChain_expressionWithSpaces(){
        String expression = "12 * 3 + 5";
        ExpressionNode[] expectedResult = {
                new NumberNode(12),
                new OperatorNode(ExpressionNodeType.Multiplication),
                new NumberNode(3),
                new OperatorNode(ExpressionNodeType.Addition),
                new NumberNode(5)
        };
        assertExpressionChain(expression, expectedResult);
    }

    @Test
    public void buildExpressionChain_expressionWithBrackets(){
        String expression = "12+(4^2)-29";
        ExpressionNode[] expectedResults = {
                new NumberNode(12),
                new OperatorNode(ExpressionNodeType.Addition),
                new BracketNode("4^2"),
                new OperatorNode(ExpressionNodeType.Subtraction),
                new NumberNode(29)
        };
        assertExpressionChain(expression, expectedResults);
    }

    @Test
    public void buildExpressionChain_incompleteBrackets_throwsException(){
        BadExpressionException exceptionThrown = assertThrows(BadExpressionException.class, () -> {
            String expression = "12+9-(54^8/(67-43)-29";
            builder.build(expression);
        });
        assertEquals(exceptionThrown.getMessage(), "The open bracket at index 5 was not closed.");
    }

    @Test
    public void buildExpressionChain_expressionWithNestedBrackets(){
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
        assertExpressionChain(expression, expectedResults);
    }

    @Test
    public void buildExpressionChain_expressionStartsWithMinus(){
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
        assertExpressionChain(expression, expectedResult);
    }

    @Test
    public void buildExpressionChain_expressionStartsWithSpacesAndMinus(){
        String expression = "   - 91 + 2^7";
        ExpressionNode[] expectedResult = {
                new NumberNode(0),
                new OperatorNode(ExpressionNodeType.Subtraction),
                new NumberNode(91),
                new OperatorNode(ExpressionNodeType.Addition),
                new NumberNode(2),
                new OperatorNode(ExpressionNodeType.Exponent),
                new NumberNode(7)
        };
        assertExpressionChain(expression, expectedResult);
    }

    @Test
    public void buildExpressionChain_expressionStartsWithPlus(){
        String expression = "+21 * 27";
        ExpressionNode[] expectedResult = {
                new NumberNode(0),
                new OperatorNode(ExpressionNodeType.Addition),
                new NumberNode(21),
                new OperatorNode(ExpressionNodeType.Multiplication),
                new NumberNode(27)
        };
        assertExpressionChain(expression, expectedResult);
    }

    private void assertExpressionChain(String expression, ExpressionNode[] expectedResults){
        try{
            ExpressionChain expressionChain = builder.build(expression);
            ExpressionChainAssert.assertNodesAreCorrect(expressionChain, expectedResults);
        } catch (BadExpressionException e) {
            e.printStackTrace();
            Assertions.fail(e);
        }
    }
}
