package com.ikemole.expressionevaluator.math.tests;

import com.ikemole.expressionevaluator.math.Factorizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class FactorizerTest {
    private static Stream<Arguments> factorizeExamples() {
        return Stream.of(
                Arguments.of(560, new int[]{2,2,2,2,5,7}),
                Arguments.of(0, new int[]{0}),
                Arguments.of(1, new int[]{1}),
                Arguments.of(2, new int[]{2}),
                Arguments.of(3, new int[]{3}),
                Arguments.of(4, new int[]{2,2}),
                Arguments.of(1482451, new int[]{17,29,31,97})
        );
    }

    @ParameterizedTest
    @MethodSource("factorizeExamples")
    public void factorizeTests(int num, int[] expectedFactors){
        int[] result = Factorizer.factorize(num);
        Assertions.assertArrayEquals(expectedFactors, result);
    }

    @Test
    public void factorize_negativeNumber_Throws(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Factorizer.factorize(-1);
        });
    }
}
