/**
85. Maximal Rectangle
Hard

Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

Example:

Input:
[
  ["1","0","1","0","0"],
  ["1","0","1","1","1"],
  ["1","1","1","1","1"],
  ["1","0","0","1","0"]
]
Output: 6

 */

import java.util.Arrays;
import java.util.ArrayDeque;

public class MaximalRectangle {

    public static void main(String[] args) {
        char[][] m = new char[][]{
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
        }; // ans = 6
//        char[][] m = new char[][]{
//            {'0', '1'}
//        }; // ans = 1

        for (char[] r : m) {
            System.out.printf("%s\n", Arrays.toString(r));
        }
        MaximalRectangle ms = new MaximalRectangle();
        int a = ms.maximalRectangle(m); 
        System.out.printf("area of maximal rectangle of 1s: %d\n", a);
    }

    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int[] heights = new int[matrix[0].length];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '0') {
                    heights[j] = 0;
                } else {
                    heights[j]++;
                }
            }
            int rmax = largestRectangle(heights);
            //System.out.printf("heights=%s, a=%d\n", Arrays.toString(heights), rmax);
            max = Math.max(max, rmax);
        }
        return max;
    }

    private int largestRectangle(int[] h) {
        if (h == null || h.length == 0) return 0;
        if (h.length == 1) return h[0];
        int max = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>(); 
        stack.push(0);
        for (int i = 1; i <= h.length; i++) {
            int hc = (i == h.length? 0 : h[i]);
            if (hc < h[i-1]) {
                while (!stack.isEmpty() && h[stack.peek()] > hc) {
                    int i0 = stack.pop(); 
                    int h0 = h[i0];
                    if (stack.isEmpty()) {
                        i0 = 0;
                    } else {
                        i0 = stack.peek() + 1;
                    }
                    max = Math.max(max, h0*(i-i0));
                }
            }
            stack.push(i);
        }
        return max;
    }

}
