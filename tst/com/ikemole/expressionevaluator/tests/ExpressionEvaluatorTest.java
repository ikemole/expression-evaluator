package com.ikemole.expressionevaluator.tests;

import com.ikemole.expressionevaluator.ExpressionEvaluator;
import com.ikemole.expressionevaluator.exception.BadExpressionException;
import com.ikemole.expressionevaluator.structure.ExpressionResultWithSteps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

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

    @Test
    public void evaluate_multiDivision(){
        assertExpressionResult("2/2/2", 0.5);
    }

    @Test
    public void evaluate_divByZero(){
        assertExpressionResult("2/0", Double.POSITIVE_INFINITY);
    }

    @Test
    public void evaluate_divByZeroNeg(){
        assertExpressionResult("(1-2)/0", Double.NEGATIVE_INFINITY);
    }

    @Test
    public void evaluate_divByZeroAddInfinity(){
        assertExpressionResult("((1-2)/0)+(2/0)", Double.NaN);
    }

    @Test
    public void evaluate_NaN(){
        assertExpressionResult("0/0", Double.NaN);
    }

    @Test
    public void test_showWorking_1(){
        String expression = "2+3*4";
        double expectedResult = 14;
        String[] expectedSteps = {
                "2+3*4",
                "2+12",
                "14"
        };
        assertEvaluateAndShowWorking(expression, expectedResult, expectedSteps);
    }

    @Test
    public void test_showWorking_singleBracket(){
        String expression = "28/(2+3*4)";
        double expectedResult = 2;
        String[] expectedSteps = {
                "28/(2+3*4)",
                "28/(2+12)",
                "28/(14)",
                "28/14",
                "2"
        };
        assertEvaluateAndShowWorking(expression, expectedResult, expectedSteps);
    }

    @Test
    public void test_showWorking_multiBracket(){
        String expression = "28/(2+3*4)+(6-7)";
        double expectedResult = 1;
        String[] expectedSteps = {
                "28/(2+3*4)+(6-7)",
                "28/(2+12)+(6-7)",
                "28/(14)+(6-7)",
                "28/14+(6-7)",
                "28/14+(-1)",
                "28/14+-1",
                "2+-1",
                "1",
        };
        assertEvaluateAndShowWorking(expression, expectedResult, expectedSteps);
    }

    @Test
    public void test_showWorking_nestedBracket(){
        String expression = "43+(4^(2+(6*3-17)))/2";
        double expectedResult = 75;
        String[] expectedSteps = {
                "43+(4^(2+(6*3-17)))/2",
                "43+(4^(2+(18-17)))/2",
                "43+(4^(2+(1)))/2",
                "43+(4^(2+1))/2",
                "43+(4^(3))/2",
                "43+(4^3)/2",
                "43+(64)/2",
                "43+64/2",
                "43+32",
                "75",
        };
        assertEvaluateAndShowWorking(expression, expectedResult, expectedSteps);
    }

    @Test
    public void test_showWorking_withInfinity(){
        String expression = "28-(2/0)";
        double expectedResult = Double.NEGATIVE_INFINITY;
        String[] expectedSteps = {
                "28-(2/0)",
                "28-(Infinity)",
                "28-Infinity",
                "-Infinity",
        };
        assertEvaluateAndShowWorking(expression, expectedResult, expectedSteps);
    }

    @Test
    public void test_showWorking_withDecimal(){
        String expression = "1/2+(3/4)+(1/11)";
        double expectedResult = 1.34;
        String[] expectedSteps = {
                "1/2+(3/4)+(1/11)",
                "1/2+(0.75)+(1/11)",
                "1/2+0.75+(1/11)",
                "1/2+0.75+(0.09)",
                "1/2+0.75+0.09",
                "0.5+0.75+0.09",
                "1.25+0.09",
                "1.34",
        };
        assertEvaluateAndShowWorking(expression, expectedResult, expectedSteps);
    }

    private void assertExpressionResult(String expression, double expectedResult){
        try {
            double actualResult = evaluator.evaluate(expression);
            Assertions.assertEquals(expectedResult, actualResult);
        } catch (BadExpressionException e) {
            e.printStackTrace();
            Assertions.fail(e);
        }
    }

    private void assertEvaluateAndShowWorking(String expression, double expectedResult, String[] expectedSteps){
        try {
            ExpressionResultWithSteps actual = evaluator.evaluateAndShowWorking(expression, true);
            Assertions.assertEquals(expectedResult, actual.getResult(), 0.001);
            Assertions.assertArrayEquals(expectedSteps, actual.getSteps().toArray(new String[0]));
        } catch (BadExpressionException e) {
            e.printStackTrace();
            Assertions.fail(e);
        }
    }
}
