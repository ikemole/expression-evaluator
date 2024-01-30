package com.ikemole.expressionevaluator.math;

import java.util.Arrays;

public class ArrayUtils {
    /**
     * Find the common values in a set of arrays.
     * @param arrays The arrays
     * @return an array of common values among them
     */
    public static int[] findCommonValues(int[][] arrays)
    {
        var pointers = new int[arrays.length];
        var common = new int[arrays[0].length];
        var icommon = 0;
        var endOfArr = false;

        while (!endOfArr){
            var allMatch = true;
            int curr = arrays[0][pointers[0]];
            for(int i=0; i < arrays.length; i++) {
                var arr = arrays[i];

                while(pointers[i] < arr.length && arr[pointers[i]] < curr){
                    pointers[i]++;
                }

                if(pointers[i] < arr.length){
                    var val = arr[pointers[i]];
                    if(val == curr){
                        pointers[i]++;
                    }
                    else if(val > curr){
                        allMatch = false;
                    }
                }
                else {
                    allMatch = false;
                }

                if(pointers[i] >= arr.length){
                    endOfArr = true;
                }
            }

            if(allMatch){
                common[icommon] = curr;
                icommon++;
            }

        }

        return Arrays.copyOfRange(common, 0, icommon);
    }

}
