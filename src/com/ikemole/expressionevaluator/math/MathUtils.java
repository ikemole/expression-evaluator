package com.ikemole.expressionevaluator.math;

import java.util.Arrays;

public class MathUtils {
    /**
     * Calculate the product of numbers.
     */
    public static int product(int[] nums){
        return Arrays.stream(nums).reduce(1, (x,y) -> x * y);
    }

    /**
     * Calculate the sum of numbers.
     */
    public static int sum(int[] nums){
        return Arrays.stream(nums).sum();
    }
}
