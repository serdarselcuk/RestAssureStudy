package com.automation.tests.practice;


import java.util.*;

public class SoapWorks {

    public static void main(String[] args) {
        int[][]arr = {   {2, 1, 7}
                        ,{9, 9, 9}
                        ,{4, 8, 6}};

        int[][]arr2 = {
                 {4, 5, 8}
                ,{2, 4, 1}
                ,{1, 9, 7}
        };
        int[][][] ar1 =   { {{8, 1, 6}, {3, 5, 7}, {4, 9, 2}},
            {{6, 1, 8}, {7, 5, 3}, {2, 9, 4}},
            {{4, 9, 2}, {3, 5, 7}, {8, 1, 6}},
            {{2, 9, 4}, {7, 5, 3}, {6, 1, 8}},
            {{8, 3, 4}, {1, 5, 9}, {6, 7, 2}},
            {{4, 3, 8}, {9, 5, 1}, {2, 7, 6}},
            {{6, 7, 2}, {1, 5, 9}, {8, 3, 4}},
            {{2, 7, 6}, {9, 5, 1}, {4, 3, 8}}};
        for (int[][] each :
                ar1) {
            System.out.println(minCost(each));
        }

       }

    static  int[][][] sampleProvider() {
        ArrayDeque<Integer> nums = new ArrayDeque<>(Arrays.asList(2, 7, 6, 1, 8, 3, 4, 9));
        int[][] positions = {{0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}, {2, 1}, {2, 0}, {1, 0}};
        int[][][] result = new int[7][3][3];
        int counterF = 0;
        int counterR = 6;

        for (int i = 0; i < 7; i ++) {

            if(nums.getFirst() == 2||nums.getFirst()==6||nums.getFirst()==4||nums.getFirst()==8){

                Iterator<Integer>it = nums.iterator();

                for (int j = 0; it.hasNext(); j++) {

                    result[counterF][positions[j][0]][positions[j][1]]= it.next();

                }
                result[counterF][1][1]=5;
                counterF++;

            }
            else if (nums.getLast() == 2||nums.getLast()==6||nums.getLast()==4||nums.getLast()==8){

                Iterator<Integer>reverseIt =nums.descendingIterator();

                for (int j = 0; reverseIt.hasNext(); j++) {

                    result[counterR][positions[j][0]][positions[j][1]]= reverseIt.next();

                }
                result[counterR][1][1]=5;
                counterR--;

            }
            nums.add(nums.poll());
            

        }
        return result;
    }
    
    static int minCost(int[][] arr){
        int[][][] sample = sampleProvider();

        int lastResult = 45;

        for (int[][] each : sample) {
            int result = 0;
            for (int i = 0; i < each.length; i++) {
                int val = 0;
                for (int j = 0; j < each[i].length; j++) {
                    int value = each[i][j]-arr[i][j];
                    value = value<0?-value:value;
                    val+=value;
                }
                result+=val;
            }
            System.out.println(Arrays.deepToString(arr)+"\n"+Arrays.deepToString(each)+"\n"+result);
            if (lastResult>result) lastResult=result;
                
            }
        return lastResult;
        }
    }



