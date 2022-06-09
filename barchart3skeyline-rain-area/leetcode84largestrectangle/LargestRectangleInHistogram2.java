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

public class LargestRectangleInHistogram2 {

    public static void main(String[] args) {
        LargestRectangleInHistogram2 l = new LargestRectangleInHistogram2();
        int[] heights = new int[]{2,1,5,6,2,3};
        // int[] heights = new int[]{2};
        // int[] heights = new int[]{2,1,2};
        int max = l.largestRectangleArea(heights);
        System.out.printf("largest rectangle area: %d of histogram: %s\n", max, Arrays.toString(heights));
    }

    /**
     * copyright credit: copied from LeetCode submissions
     */
    public int largestRectangleArea(int[] h) {
        int n = h.length;
        if(n == 0)
            return 0;
        int[] l = new int[n];
        int[] r = new int[n];

        l[0] = -1;
        for(int i = 1; i < n ; i++) {
            int pos = i - 1;
            while(pos >= 0 && h[i] <= h[pos]) {
                pos =  l[pos];
            }
            l[i] = pos;
        }

        r[n-1] = n;
        for(int i = n - 2; i >=0; i--) {
            int pos = i + 1;
            while(pos < n && h[i] <= h[pos]) {
                pos = r[pos];
            }
            r[i] = pos;
        }
        // System.out.println(Arrays.toString(h));
        // System.out.println(Arrays.toString(l));
        // System.out.println(Arrays.toString(r));
        int area = 0;
        for(int i = 0; i < n ; i++) {
            int width = r[i] - l[i] - 1;
            area = Math.max(area, width * h[i]);
        }

        return area;
    }
}
