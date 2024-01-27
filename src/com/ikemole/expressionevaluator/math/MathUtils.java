package com.ikemole.expressionevaluator.math;

public class MathUtils {
    public static int multiply(int[] nums){
        int result = 1;
        for (int num : nums) {
            result = result * num;
        }
        return result;
    }
}
