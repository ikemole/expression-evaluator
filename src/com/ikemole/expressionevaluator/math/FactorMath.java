package com.ikemole.expressionevaluator.math;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A utility that deals with factors of a number.
 */
public class FactorMath {
    /**
     * Find the prime factors of a number.
     * @param num The number to be factorized
     * @return an array of the prime factors
     */
    public static int[] factorize(int num){
        if (num < 0){
            throw new IllegalArgumentException("number is less than 0");
        }

        if (num <= 1){
            return new int[]{num};
        }

        int[] factors = new int[100];
        int i = 0;
        int factor = 2;
        while (num > 1){
            while (num % factor == 0 && num > 1){
                factors[i] = factor;
                i++;
                num = num / factor;
            }

            factor++;
        }

        return Arrays.copyOfRange(factors, 0, i);
    }

    /**
     * Calculate the Highest Common Factor of a group of numbers.
     * @param nums Numbers to calculate HCF for
     * @return the HCF
     */
    public static int calcHCF(int[] nums){
        int[][] factors = new int[nums.length][];
        for (int i = 0; i < nums.length; i++) {
            factors[i] = factorize(nums[i]);
        }
        var commonFactors = ArrayUtils.findCommonValues(factors);
        return MathUtils.multiply(commonFactors);
    }

    /**
     * Calculate the LCM of a set of numbers
     * @param nums The numbers
     * @return
     */
    public static int calcLCM(int[] nums){
        int[][] factors = new int[nums.length][];
        for (int i = 0; i < nums.length; i++) {
            factors[i] = factorize(nums[i]);
        }

        var factorCounts = new HashMap<Integer, Integer>();
        for (int[] arr : factors) {
            var arrMap = new HashMap<Integer, Integer>();
            for (int num : arr) {
                if (!arrMap.containsKey(num)) {
                    arrMap.put(num, 1);
                } else {
                    arrMap.put(num, arrMap.get(num) + 1);
                }

                if (!factorCounts.containsKey(num)) {
                    factorCounts.put(num, 1);
                } else {
                    if (factorCounts.get(num) < arrMap.get(num)) {
                        factorCounts.put(num, factorCounts.get(num) + 1);
                    }
                }
            }
        }

        int lcm = 1;
        for(var entry : factorCounts.entrySet()){
            lcm = lcm * (int)Math.pow(entry.getKey(), entry.getValue());
        }
        return lcm;
    }
}
