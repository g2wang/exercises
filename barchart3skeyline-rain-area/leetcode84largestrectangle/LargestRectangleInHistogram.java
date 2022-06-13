/*
LeetCode: 84. Largest Rectangle in Histogram
Hard
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

public class LargestRectangleInHistogram {

    public static void main(String[] args) {
        LargestRectangleInHistogram l = new LargestRectangleInHistogram();
        // int[] heights = new int[]{2,1,5,6,2,3};
        // int[] heights = new int[]{2};
        int[] heights = new int[]{2,1,2};
        int max = l.largestRectangleArea(heights);
        System.out.printf("largest rectangle area: %d of histogram: %s\n", max, Arrays.toString(heights));
    }

    /**
     * time: 7 ms, faster than 99.90%
     * memory: 40.7MB
     */
    public int largestRectangleArea(int[] heights) {
        if (heights.length == 0) return 0;
        int max = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        for (int i = 1; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                int idx = stack.pop();
                int left = stack.isEmpty()?0:stack.peek()+1;
                max = Math.max(max, (i-left)*heights[idx]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int idx = stack.pop();
            int left = stack.isEmpty()?0:stack.peek()+1;
            max = Math.max(max, (heights.length-left)*heights[idx]);
        }
        return max;
    }

}
