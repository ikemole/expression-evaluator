package com.ikemole.expressionevaluator.math;

import java.util.Arrays;

/**
 * A utility used to factorize a number.
 */
public class Factorizer {
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
}
