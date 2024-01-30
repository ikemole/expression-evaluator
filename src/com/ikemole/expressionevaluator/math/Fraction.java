package com.ikemole.expressionevaluator.math;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a mathematical fraction. A fraction consists of
 * a numerator (the number above the line) and a denominator (the number below the line).
 * For example: 1/2, 45/17, etc.
 */
public class Fraction {
    private final int numerator;
    private final int denominator;

    private static final Pattern PARSE_FRACTION_REGEX = Pattern.compile("\\s*(-?\\d+)\\s*/\\s*([1-9][0-9]*)\\s*");

    private static final Fraction ZERO_FRACTION = new Fraction(0, 1);

    private static final Fraction ONE_FRACTION = new Fraction(1, 1);

    public Fraction(int numerator, int denominator) {
        if (denominator <= 0)
            throw new InvalidFractionException(FractionError.INVALID_DENOMINATOR);

        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Create a fraction from its string representation.
     * @param fractionStr the string representation of a fraction
     * @return the Fraction object
     */
    public static Fraction Parse(String fractionStr) {
        final Matcher matcher = PARSE_FRACTION_REGEX.matcher(fractionStr);
        if (matcher.matches() && matcher.groupCount() == 2){
            var numerator = Integer.parseInt(matcher.group(1));
            var denominator = Integer.parseInt(matcher.group(2));
            return new Fraction(numerator, denominator);
        }
        else {
            throw new InvalidFractionException(FractionError.INVALID_FRACTION_STRING);
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

        int hcf = FactorMath.calculateHCF(new int[]{numerator, denominator});
        int finalNum = numerator/hcf;
        int finalDen = denominator/hcf;
        return new Fraction(finalNum, finalDen);
    }

    /**
     * Get fraction as number
     * @return
     */
    public double asNumber(){
        return (double) numerator / denominator;
    }

    /**
     * Add up an array of fractions.
     * @param fractions the fractions to add
     */
    public static Fraction add(Fraction[] fractions){
        var denominators = Arrays.stream(fractions).mapToInt(Fraction::getDenominator).toArray();
        var denominatorLcm = FactorMath.calculateLCM(denominators);
        var newNumerators = new int[fractions.length];
        for (int i = 0; i < fractions.length; i++) {
            newNumerators[i] = (denominatorLcm/fractions[i].getDenominator()) * fractions[i].getNumerator();
        }
        var finalNumerator = MathUtils.sum(newNumerators);
        var finalFraction = new Fraction(finalNumerator, denominatorLcm);
        return finalFraction.simplify();
    }

    /**
     * Multiply an array of fractions.
     * @param fractions the fractions to add
     */
    public static Fraction multiply(Fraction[] fractions){
        fractions = Arrays.stream(fractions).map(Fraction::simplify).toArray(Fraction[]::new);
        var numerators = Arrays.stream(fractions).mapToInt(Fraction::getNumerator).toArray();
        var denominators = Arrays.stream(fractions).mapToInt(Fraction::getDenominator).toArray();
        var newNumerator = MathUtils.product(numerators);
        var newDenominator = MathUtils.product(denominators);
        var finalFraction = new Fraction(newNumerator, newDenominator);
        return finalFraction.simplify();
    }

    /**
     * Subtract one fraction from another
     * @param minuend The fraction to be subtracted from
     * @param subtrahend The number to subtract
     * @return the result
     */
    public static Fraction subtract(Fraction minuend, Fraction subtrahend) {
        var denominatorLcm = FactorMath.calculateLCM(new int[]{minuend.getDenominator(), subtrahend.getDenominator()});
        var newMinuendNumerator = (denominatorLcm / minuend.getDenominator()) * minuend.getNumerator();
        var newSubtrahendNumerator = (denominatorLcm / subtrahend.getDenominator()) * subtrahend.getNumerator();
        var finalFraction = new Fraction(newMinuendNumerator - newSubtrahendNumerator, denominatorLcm);
        return finalFraction.simplify();
    }

    /**
     * Divide one fraction by another
     * @param dividend The number to be divided
     * @param divisor The number to divide it by
     * @return the quotient
     */
    public static Fraction divide(Fraction dividend, Fraction divisor){
        if (divisor.numerator == 0){
            throw new ArithmeticException("Cannot divide by zero.");
        }

        var finalNumerator = dividend.numerator * divisor.denominator;
        var finalDenominator = dividend.denominator * divisor.numerator;

        // Move negative sign to the numerator, if necessary
        if (finalDenominator < 0){
            finalDenominator = Math.abs(finalDenominator);
            finalNumerator = -1 * finalNumerator;
        }

        var finalFraction = new Fraction(finalNumerator, finalDenominator);
        return finalFraction.simplify();
    }

}
