package com.ikemole.expressionevaluator.math.tests;

import com.ikemole.expressionevaluator.math.Fraction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FractionTest {

    private static Stream<Arguments> fractionStringExamples() {
        return Stream.of(
                Arguments.of(2, 3, "2/3"),
                Arguments.of(1, 1, "1/1"),
                Arguments.of(27, 3, "27/3"),
                Arguments.of(7, 17, "7/17")
        );
    }

    @ParameterizedTest
    @MethodSource("fractionStringExamples")
    public void toStringTest(int numerator, int denominator, String expected){
        Fraction fraction = new Fraction(numerator, denominator);
        assertEquals(expected, fraction.toString());
    }

    @ParameterizedTest
    @MethodSource("fractionStringExamples")
    public void fromStringTest(int expNumerator, int expDenominator, String fractionStr){
        Fraction fraction = new Fraction(fractionStr);
        assertEquals(expNumerator, fraction.getNumerator());
        assertEquals(expDenominator, fraction.getDenominator());
    }

    private static Stream<Arguments> simplifyExamples() {
        return Stream.of(
                Arguments.of("8/12", "2/3"),
                Arguments.of("70/7", "10/1"),
                Arguments.of("7/70", "1/10"),
                Arguments.of("3/3", "1/1"),
                Arguments.of("1/1", "1/1"),
                Arguments.of("1/4", "1/4"),
                Arguments.of("2/4", "1/2"),
                Arguments.of("13/39", "1/3"),
                Arguments.of("40/100", "2/5"),
                Arguments.of("273/8900", "273/8900"),
                Arguments.of("273/6230", "39/890"),
                Arguments.of("0/5", "0/1"),
                Arguments.of("49/1", "49/1")
        );
    }

    @ParameterizedTest
    @MethodSource("simplifyExamples")
    public void simplifyFractionTest(String input, String expected){
        Fraction fraction = new Fraction(input);
        Fraction simplified = fraction.simplify();
        assertEquals(expected, simplified.toString());
    }

    private static Stream<Arguments> addExamples(){
        return Stream.of(
                Arguments.of(new String[]{"1/3"}, "1/3"),
                Arguments.of(new String[]{"1/2", "1/2"}, "1/1"),
                Arguments.of(new String[]{"1/1", "1/1"}, "2/1"),
                Arguments.of(new String[]{"5/1", "1/5"}, "26/5"),
                Arguments.of(new String[]{"1/3", "1/4"}, "7/12"),
                Arguments.of(new String[]{"1/3", "1/4", "1/12"}, "2/3"),
                Arguments.of(new String[]{"1/3", "1/4", "1/12", "7/21"}, "1/1")
        );
    }

    @ParameterizedTest
    @MethodSource("addExamples")
    public void addFractionTest(String[] fractionsToAdd, String expectedResult){
        Fraction[] fractions = Arrays.stream(fractionsToAdd).map(Fraction::new).toArray(Fraction[]::new);
        var result = Fraction.add(fractions);
        assertEquals(expectedResult, result.toString());
    }

    private static Stream<Arguments> subtractExamples(){
        return Stream.of(
                Arguments.of(new String[]{"1/2", "1/2"}, "0/1"),
                Arguments.of(new String[]{"1/1", "1/1"}, "0/1"),
                Arguments.of(new String[]{"26/5", "1/5"}, "5/1"),
                Arguments.of(new String[]{"26/5", "5/1"}, "1/5"),
                Arguments.of(new String[]{"7/12", "1/4"}, "1/3"),
                Arguments.of(new String[]{"7/12", "1/3"}, "1/4"),
                Arguments.of(new String[]{"3/3", "1/12"}, "11/12")
        );
    }

    @ParameterizedTest
    @MethodSource("subtractExamples")
    public void subtractFractionTest(String[] subtractArgs, String expectedResult){
        Fraction fraction1 = new Fraction(subtractArgs[0]);
        Fraction fraction2 = new Fraction(subtractArgs[1]);
        var result = Fraction.subtract(fraction1, fraction2);
        assertEquals(expectedResult, result.toString());
    }

    private static Stream<Arguments> divideExamples(){
        return Stream.of(
                Arguments.of(new String[]{"1/2", "1/2"}, "1/1"),
                Arguments.of(new String[]{"26/5", "1/5"}, "26/1"),
                Arguments.of(new String[]{"7/12", "2/1"}, "7/24"),
                Arguments.of(new String[]{"3/3", "1/12"}, "12/1")
        );
    }

    @ParameterizedTest
    @MethodSource("divideExamples")
    public void divideFractionTest(String[] divideArgs, String expectedResult){
        Fraction dividend = new Fraction(divideArgs[0]);
        Fraction divisor = new Fraction(divideArgs[1]);
        var result = Fraction.divide(dividend, divisor);
        assertEquals(expectedResult, result.toString());
    }

    private static Stream<Arguments> multiplyExamples(){
        return Stream.of(
                Arguments.of(new String[]{"1/2", "1/2"}, "1/4"),
                Arguments.of(new String[]{"26/5", "5/26"}, "1/1"),
                Arguments.of(new String[]{"7/12", "2/1"}, "7/6"),
                Arguments.of(new String[]{"3/3", "1/12"}, "1/12"),
                Arguments.of(new String[]{"1/3", "2/7", "4/8"}, "1/21")
        );
    }

    @ParameterizedTest
    @MethodSource("multiplyExamples")
    public void multiplyFractionTest(String[] multiplyArgs, String expectedResult){
        Fraction[] fractions = Arrays.stream(multiplyArgs).map(Fraction::new).toArray(Fraction[]::new);
        var result = Fraction.multiply(fractions);
        assertEquals(expectedResult, result.toString());
    }
}
