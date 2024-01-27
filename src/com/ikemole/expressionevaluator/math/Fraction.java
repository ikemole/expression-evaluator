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

    private static final Fraction ZERO_FRACTION = new Fraction(0, 1);
    private static final Fraction ONE_FRACTION = new Fraction(1, 1);

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

    /**
     * Get a simpler representation of the fraction.
     * For example, "2/4" becomes "1/2".
     * @return the new fraction
     */
    public Fraction simplify(){
        if (numerator == 0){
            return ZERO_FRACTION;
        }

        if (denominator == 1){
            return this;
        }

        if (numerator == denominator){
            return ONE_FRACTION;
        }

        int hcf = FactorMath.calcHCF(new int[]{numerator, denominator});
        int finalNum = numerator/hcf;
        int finalDen = denominator/hcf;
        return new Fraction(finalNum, finalDen);
    }

    /**
     * Add up an array of fractions.
     * @param fractions
     */
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

    /**
     * Calculate fraction1 minus fraction2
     * @param fraction1
     * @param fraction2
     * @return
     */
    public static Fraction subtract(Fraction fraction1, Fraction fraction2) {
        var denominatorLcm = FactorMath.calcLCM(new int[]{fraction1.getDenominator(), fraction2.getDenominator()});
        var newFraction1Numerator = (denominatorLcm / fraction1.getDenominator()) * fraction1.getNumerator();
        var newFraction2Numerator = (denominatorLcm / fraction2.getDenominator()) * fraction2.getNumerator();
        var finalFraction = new Fraction(newFraction1Numerator - newFraction2Numerator, denominatorLcm);
        return finalFraction.simplify();
    }

}
