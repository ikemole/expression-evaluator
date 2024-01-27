package com.ikemole.expressionevaluator.math.tests;

import com.ikemole.expressionevaluator.math.ArrayUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ArrayUtilsTest {
    private static Stream<Arguments> findCommonTestData() {
        return Stream.of(
                Arguments.of(
                        new int[][]{
                                {2, 4, 5},
                                {2, 5, 7},
                                {2, 3, 5}
                        },
                        new int[]{2, 5}
                ),
                Arguments.of(
                        new int[][]{
                                {2, 4, 5, 9, 17, 19},
                                {4, 5, 9},
                                {2, 3, 4, 7, 8, 9, 13}
                        },
                        new int[]{4, 9}
                ),
                Arguments.of(
                        new int[][]{
                                {2, 3, 3, 7},
                                {3, 3, 5},
                        },
                        new int[]{3, 3}
                ),
                Arguments.of(
                        new int[][]{
                                {7},
                                {5},
                        },
                        new int[]{}
                ),
                Arguments.of(
                        new int[][]{
                                {7, 13, 89, 100},
                                {5, 12, 45, 60, 99},
                        },
                        new int[]{}
                ),
                Arguments.of(
                        new int[][]{
                                {7, 13, 89, 100},
                                {5, 12, 100, 109, 203},
                                {15, 32, 45, 78, 97, 100, 109},
                        },
                        new int[]{100}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("findCommonTestData")
    public void findCommonValues(int[][] arrays, int[] expectedCommon){
        int[] result = ArrayUtils.findCommonValues(arrays);
        assertArrayEquals(expectedCommon, result);
    }
}
