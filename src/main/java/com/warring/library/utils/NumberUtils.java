package com.warring.library.utils;

import com.warring.library.pair.Pair;

public class NumberUtils {

    public static Pair<Boolean, Integer> validInteger(String arg) {
        Pair<Boolean, Integer> pair;
        int i;
        try {
            i = Integer.parseInt(arg);
        } catch (NumberFormatException ex) {
            pair = new Pair<>(false, -1);
            return pair;
        }
        pair = new Pair<>(true, i);
        return pair;
    }

    public static Pair<Boolean, Double> validDouble(String arg) {
        Pair<Boolean, Double> pair;
        double i;
        try {
            i = Double.valueOf(arg);
        } catch (NumberFormatException ex) {
            pair = new Pair<>(false, -0.0);
            return pair;
        }
        pair = new Pair<>(true, i);
        return pair;
    }
}
