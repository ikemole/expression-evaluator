package com.ikemole.expressionevaluator.tests;

import com.ikemole.expressionevaluator.ExpressionEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionEvaluatorTest {
    private ExpressionEvaluator evaluator = new ExpressionEvaluator();

    @Test
    public void evaluate_simpleExpressions(){
        assertExpressionResult("2*3", 6);
        assertExpressionResult("5+2*3", 11);
        assertExpressionResult("5+2^4*3-1", 52);
    }

    @Test
    public void evaluate_expressionsWithBrackets(){
        assertExpressionResult("(2*3)", 6);
        assertExpressionResult("(5+2)*3", 21);
        assertExpressionResult("5+2^(4+3)-1", 132);
    }

    @Test
    public void evaluate_expressionsWithNestedBrackets() {
        assertExpressionResult("43 + (4 ^ (2 + 1)) - 9 ^ (9 - 7)", 26);
        assertExpressionResult("(5*2*7) + (2 ^ (2 + (81/27))) - 4 ^ (1/2)", 100);
    }

    @Test
    public void evaluate_expressionsWithStartingMinusSign(){
        assertExpressionResult("-1+2+3", 4);
        assertExpressionResult("-1+2-3", -2);
        assertExpressionResult("-1-2-3", -6);
        assertExpressionResult("-1-2-3-4+5-6-7-8-9+10", -25);
        assertExpressionResult("-1 + 2^7", 127);
        assertExpressionResult("-15*2^3-5", -125);
        assertExpressionResult("-15*2^3+5", -115);
    }

    @Test
    public void evaluate_minusExponents(){
        assertExpressionResult("(-2)^4", 16);
        assertExpressionResult("-2^4", -16);
    }

    private void assertExpressionResult(String expression, double expectedResult){
        double actualResult = evaluator.evaluate(expression);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
