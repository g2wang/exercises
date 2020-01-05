/*
LeetCode: 84. Largest Rectangle in Histogram
Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.
        _
      _| |
     | | |
     | | |  _
  _  | | |_| |
 | |_| | | | |
 |_|_|_|_|_|_|
 

Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].

        _
      _| |
     |/|/|
     |/|/|  _
  _  |/|/|_| |
 | |_|/|/| | |
 |_|_|/|/|_|_|
 
The largest rectangle is shown in the shaded area, which has area = 10 unit.


Example:
Input: [2,1,5,6,2,3]
Output: 10

Example 2:
Input: [2,1,2]
Output: 3 

*/

import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

public class LargestRectangleInHistogram3 {

    public static void main(String[] args) {
        LargestRectangleInHistogram3 l = new LargestRectangleInHistogram3();
        int[] heights = new int[]{2,1,5,6,2,3};
        // int[] heights = new int[]{2};
        // int[] heights = new int[]{2,1,2};
        int max = l.largestRectangleArea(heights);
        System.out.printf("largest rectangle area: %d of histogram: %s\n", max, Arrays.toString(heights));
    }

    /**
     * copyright: the author of one submission to leetcode problem 84
     */
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        return lra(heights, 0, heights.length);
    }

    private int lra(int[] heights, int start, int end) {
        if (end - start == 1) {
            return heights[start];
        }
        // 1. find the min bar, compute basis max by min * heights.length
        boolean ascd = true;
        boolean desc = true;
        int min = heights[start];
        for (int i = start + 1; i < end; i++) {
            if (heights[i] < min) {
                min = heights[i];
            }
            if (heights[i] < heights[i-1]) {
                ascd = false;
            } else if (heights[i] > heights[i-1]) {
                desc = false;
            }
        }
        int max = min * (end - start);
        // 2. divide the histogram into several hills, each of which is
        // separated by 2 min bars as (start - 1) and (end), i.e. the range of
        // each hill is [start, end).
        if (ascd) { // only one ascending hill
            for (int i = start + 1; i < end; i++) {
                if (heights[i] == heights[i-1]) continue;
                int tmp = heights[i] * (end - i);
                if (tmp > max) {
                    max = tmp;
                }
            }
        } else if (desc) { // only one descending hill
            for (int i = end - 2; i >= start; i--) {
                if (heights[i] == heights[i+1]) continue;
                int tmp = heights[i] * (i - start + 1);
                if (tmp > max) {
                    max = tmp;
                }
            }
        } else { // divide and conquer each hill
            boolean getStart = false;
            for (int i = start; i < end; i++) {
                if (!getStart) {
                    if (heights[i] == min) continue;
                    start = i;
                    getStart = true;
                } else {
                    if (heights[i] != min) continue;
                    int tmp = lra(heights, start, i);
                    if (tmp > max) {
                        max = tmp;
                    }
                    getStart = false;
                }
            }
            if (getStart) { // the rightmost hill
                int tmp = lra(heights, start, end);
                if (tmp > max) {
                    max = tmp;
                }
            }
        }
        return max;
    }

}
