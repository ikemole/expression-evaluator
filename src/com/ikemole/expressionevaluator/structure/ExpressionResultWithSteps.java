package com.ikemole.expressionevaluator.structure;

import java.util.List;

public class ExpressionResultWithSteps {
    private double result;
    private List<String> steps;

    public ExpressionResultWithSteps(double result, List<String> steps){
        this.result = result;
        this.steps = steps;
    }

    public double getResult() {
        return result;
    }

    public List<String> getSteps() {
        return steps;
    }
}
