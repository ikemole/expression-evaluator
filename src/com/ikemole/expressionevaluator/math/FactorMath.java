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
        if (num == 0){
            return new int[]{num};
        }

        int[] factors = new int[100];
        int i = 0;
        if (num < 0){
            factors[0] = -1;
            i++;
            num  = Math.abs(num);
        }

        // always include 1
        factors[i] = 1;
        i++;

        // start checking for factors from 2
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
    public static int calculateHCF(int[] nums){
        int[][] factors = factorizeNumbers(nums, true);
        var commonFactors = ArrayUtils.findCommonValues(factors);
        return MathUtils.product(commonFactors);
    }

    /**
     * Calculate the LCM of a set of numbers
     * @param nums The numbers
     * @return The LCM of the numbers
     */
    public static int calculateLCM(int[] nums){
        int[][] factors = factorizeNumbers(nums, true);

        var lcmFactors = new HashMap<Integer, Integer>();
        for (int[] numFactors : factors) {
            var numFactorMap = new HashMap<Integer, Integer>();
            for (int num : numFactors) {
                if (!numFactorMap.containsKey(num)) {
                    numFactorMap.put(num, 1);
                } else {
                    numFactorMap.put(num, numFactorMap.get(num) + 1);
                }

                if (!lcmFactors.containsKey(num)) {
                    lcmFactors.put(num, 1);
                } else {
                    if (lcmFactors.get(num) < numFactorMap.get(num)) {
                        lcmFactors.put(num, lcmFactors.get(num) + 1);
                    }
                }
            }
        }

        int lcm = 1;
        for(var entry : lcmFactors.entrySet()){
            lcm = lcm * (int)Math.pow(entry.getKey(), entry.getValue());
        }
        return lcm;
    }

    /**
     * Factorize a set of numbers
     * @param nums The numbers
     * @param ignoreSign Ignore the negative sign of each number
     */
    private static int[][] factorizeNumbers(int[] nums, boolean ignoreSign) {
        int[][] factors = new int[nums.length][];
        for (int i = 0; i < nums.length; i++) {
            var num = ignoreSign ? Math.abs(nums[i]) : nums[i];
            factors[i] = factorize(num);
        }
        return factors;
    }
}
