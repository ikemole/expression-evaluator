package com.ikemole.expressionevaluator.math;

import java.util.Arrays;
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
        int hcf = FactorMath.calcHCF(new int[]{numerator, denominator});
        int finalNum = numerator/hcf;
        int finalDen = denominator/hcf;
        return new Fraction(finalNum, finalDen);
    }

    public static Fraction add(Fraction[] fractions){
        var denominators = Arrays.stream(fractions).mapToInt(Fraction::getDenominator).toArray();
        var denominatorLcm = FactorMath.calcLCM(denominators);
        var newNumerators = new int[fractions.length];
        for (int i = 0; i < fractions.length; i++) {
            newNumerators[i] = (denominatorLcm/fractions[i].getDenominator()) * fractions[i].getNumerator();
        }
        var finalNumerator = MathUtils.add(newNumerators);
        var finalFraction = new Fraction(finalNumerator, denominatorLcm);
        return finalFraction.simplify();
    }

}
