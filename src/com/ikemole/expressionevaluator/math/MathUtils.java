package com.ikemole.expressionevaluator.math;

public class MathUtils {
    public static int multiply(int[] nums){
        int result = 1;
        for (int num : nums) {
            result = result * num;
        }
        return result;
    }

    public static int add(int[] nums){
        int result = 0;
        for (int num : nums) {
            result += num;
        }
        return result;
    }
}
