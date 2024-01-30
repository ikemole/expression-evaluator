package com.ikemole.expressionevaluator.math.tests;

import com.ikemole.expressionevaluator.math.FactorMath;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class FactorMathTest {
    private static Stream<Arguments> factorizeExamples() {
        return Stream.of(
                Arguments.of(560, new int[]{1,2,2,2,2,5,7}),
                Arguments.of(0, new int[]{0}),
                Arguments.of(1, new int[]{1}),
                Arguments.of(2, new int[]{1,2}),
                Arguments.of(3, new int[]{1,3}),
                Arguments.of(4, new int[]{1,2,2}),
                Arguments.of(1482451, new int[]{1,17,29,31,97}),
                Arguments.of(-4, new int[]{-1,1,2,2}),
                Arguments.of(-1, new int[]{-1,1})
        );
    }

    @ParameterizedTest
    @MethodSource("factorizeExamples")
    public void factorizeTests(int num, int[] expectedFactors){
        int[] result = FactorMath.factorize(num);
        assertArrayEquals(expectedFactors, result);
    }

    private static Stream<Arguments> hcfExamples() {
        return Stream.of(
                Arguments.of(new int[]{18, 4}, 2),
                Arguments.of(new int[]{12, 18}, 6),
                Arguments.of(new int[]{400, 625}, 25),
                Arguments.of(new int[]{4, 5}, 1),
                Arguments.of(new int[]{198, 360}, 18),
                Arguments.of(new int[]{-12, -18}, 6),
                Arguments.of(new int[]{-12, 18}, 6),
                Arguments.of(new int[]{12, -18}, 6)
        );
    }

    @ParameterizedTest
    @MethodSource("hcfExamples")
    public void calcHCFTests(int[] nums, int expectedHcf){
        var actual = FactorMath.calculateHCF(nums);
        assertEquals(expectedHcf, actual);
    }

    private static Stream<Arguments> lcmExamples() {
        return Stream.of(
                Arguments.of(new int[]{18, 4}, 36),
                Arguments.of(new int[]{4,6,8}, 24),
                Arguments.of(new int[]{4, 5}, 20),
                Arguments.of(new int[]{16, 25, 100}, 400),
                Arguments.of(new int[]{-8, 20}, 40),
                Arguments.of(new int[]{-8, -20}, 40)
        );
    }

    @ParameterizedTest
    @MethodSource("lcmExamples")
    public void calcLCMTests(int nums[], int expectedLcm){
        var actual = FactorMath.calculateLCM(nums);
        assertEquals(expectedLcm, actual);
    }
}
