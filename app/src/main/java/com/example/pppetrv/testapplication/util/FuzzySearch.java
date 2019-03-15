package com.example.pppetrv.testapplication.util;

public class FuzzySearch {

    public boolean search(String pattern, String src) {
        if (pattern == null || src == null || pattern.length() == 0) {
            return false;
        }
        int j = 0;
        int length = src.length();
        int pLength = pattern.length();
        boolean result = false;
        char p = 0;
        for (int i = 0; i < length; i++) {
            char c = src.charAt(i);
            if (j < pLength) {
                p = pattern.charAt(j);
            }
            if (c == p && j < pLength) { j++; }
            if (j == pLength) {
                result = true;
                break;
            }
        }
        return result;
    }
}
