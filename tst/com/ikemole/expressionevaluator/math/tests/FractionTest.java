package com.ikemole.expressionevaluator.math.tests;

import com.ikemole.expressionevaluator.math.Fraction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
                Arguments.of("273/6230", "39/890")
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
                Arguments.of(new String[]{"1/2", "1/2"}, "1/1"),
                Arguments.of(new String[]{"1/1", "1/1"}, "2/1"),
                Arguments.of(new String[]{"1/3", "1/4"}, "7/12")
        );
    }

    @ParameterizedTest
    @MethodSource("addExamples")
    public void addFractionTest(String[] fractionsToAdd, String expectedResult){
        var fraction1 = new Fraction(fractionsToAdd[0]);
        var fraction2 = new Fraction(fractionsToAdd[1]);
        var result = Fraction.add(fraction1, fraction2);
        assertEquals(expectedResult, result.toString());
    }
}
