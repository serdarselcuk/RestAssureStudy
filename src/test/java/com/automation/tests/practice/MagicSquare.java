package com.automation.tests.practice;

public class MagicSquare {
    static int formingMagicSquare(int[][] s) {







        return s.length;

    }

    public static void main(String[] args) {

        int[][] arr = {
                {4, 8, 2},
                {4, 5, 7},
                {6, 1, 6}
        };

        System.out.println(formingMagicSquare(arr));
    }
}