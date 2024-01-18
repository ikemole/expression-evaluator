package com.ikemole.expressionevaluator.math;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This represents a fraction
 */
public class Fraction {
    private final int numerator;
    private final int denominator;

    private static final Pattern parseFractionPattern = Pattern.compile("\\s*(\\d+)\\s*\\/\\s*(\\d+)\\s*");

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(String fraction) {
        final Matcher matcher = parseFractionPattern.matcher(fraction);
        if (matcher.matches() && matcher.groupCount() == 2){
            this.numerator = Integer.parseInt(matcher.group(1));
            this.denominator = Integer.parseInt(matcher.group(2));
        }
        else{
            throw new IllegalArgumentException("The fraction string is invalid: " + fraction);
        }
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    @Override
    public String toString(){
        return String.format("%s/%s", numerator, denominator);
    }

    public Fraction simplify(){
        int[] n = Factorizer.factorize(numerator);
        int[] d = Factorizer.factorize(denominator);

        int ni = 0;
        int di = 0;

        while(ni < n.length && di < d.length){
            if(n[ni] == d[di]){
                n[ni] = 1;
                d[di] = 1;
                ni++;
                di++;
            }
	        else if(n[ni] < d[di]){
                ni++;
            }
	        else{
                di++;
            }
        }

        int finalNum = multiply(n);
        int finalDen = multiply(d);

        return new Fraction(finalNum, finalDen);
    }

    private static int multiply(int[] nums){
        int result = 1;
        for (int num : nums) {
            result = result * num;
        }
        return result;
    }
}
