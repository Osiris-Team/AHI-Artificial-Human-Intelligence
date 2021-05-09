package com.osiris.ai.utils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class IDGenerator {

    /**
     *
     * @param n amount of digits
     * @param r amount of maximum combinations
     * @return
     */
    public List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }

    /**
     * Generates an array containing
     * unique ids.
     * These ids have the smallest
     * size possible.
     * @param amount of ids we want to return in the array.
     * @return
     */
    public int[] getUniqueIds(int amount){
        int[] arrayResultIds = new int[amount];
        // We add stuff to the array until its full and throws an exception
        // Create a list which holds the current id.
        // Example: We are at value 001 so this list looks like [0,0,1]
        // We use a list so we can easily add
        // a new value to the front if the value before reached 9.
        // Example: Iterate and update the list with the iterated value.
        // When the value is 9, set it to 0 and add a 0 to the front:
        // Result: ...[7] [8] [9] [0,0] [0,1]...
        ArrayList<Integer> list = new ArrayList<>();
        // Start at 0.
        list.add(0);

        // This while loop adds each resultID to the resultIDs array.
        int i = 0;
        while (i < amount) {

            // Go through each of the queues values
            // and iterate each of them if the value is not 9.
            // If it is 9 set that value to 0 and add
            // a new value to the position before.
            // Example for list: [9]
            // Result after loop: [0,0]
            // Another example for list: [0,1]
            // Result after loop: [2,0]
            int val = list.get(0);
            // Example for [0,1]:
            // Size is 2, so we loop twice.
            // We loop backwards, so that 1 is in the first loop and 0 in the second one.
            // We increment the value but also increment the values from the loops before.
            // We increment the current value and increment all the other values.
            for (int j = 0; j < 10; j++) {

                // Insert/add the ID into the results array
                System.out.println(list.toString());
                arrayResultIds[i] = parseListElementsToInt(list);
                amount++;

                // Loop through all other values and increment them too.
                // We loop backwards and exclude the first value(by <1 instead of <0), because we already got the parent loop for incrementing that.
                for (int k = list.size(); k > 1; k--) {
                    int valChild = list.get(k-1);

                    for (int l = 0; l < 10; l++) {
                        System.out.println(list.toString());
                        arrayResultIds[i] = parseListElementsToInt(list);
                        amount++;

                        valChild++;
                        list.set(k, valChild);
                    }
                    // After looping reset and add
                    valChild = 0;
                    list.set(k, valChild);
                }

                val++; // Increment this value
                list.set(0, val);
            }
            // After looping reset and add
            val = 0;
            list.set(0, val);
            list.add(0, 0);
            /*
            for (int j = list.size(); j > 0; j--) {
                val = list.get(j);
                for (int k = 0; k < list.size() - j; k++) {

                }
                for (int k = 0; k < 10; k++) {

                    // If this value is 9 and the first one in the list,
                    // set it to 0 and add a new 0 value to the front.
                    if (val == 9 && j == 0) {
                        val = 0;
                        list.set(j, val);
                        list.add(0, 0);
                    } else {
                        val++; // Else increment the value and update it in the list.
                        list.set(j, val);
                    }
                    System.out.println(list.toString());
                    // Finally insert/add the ID into the results array
                    arrayResultIds[i] = parseListElementsToInt(list);
                    i++;
                }

             */
        }
        return arrayResultIds;
    }

    /**
     * Parses a lists elements to int.
     * Example:
     * List: [0,1,2,3]
     * Returned int: 0123
     * @param list list to parse.
     */
    private int parseListElementsToInt(List<Integer> list){
        StringBuilder resultIDStringBuilder = new StringBuilder();
        for (int queueValue :
                list) {
            resultIDStringBuilder.append(queueValue);
        }
        return Integer.parseInt(resultIDStringBuilder.toString());
    }
}
