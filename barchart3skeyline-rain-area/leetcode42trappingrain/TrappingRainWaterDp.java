/**
42. Trapping Rain Water
Hard

https://leetcode.com/problems/trapping-rain-water/

Given n non-negative integers representing an elevation map where the width of
each bar is 1, compute how much water it is able to trap after raining.

The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In
this case, 6 units of rain water (blue section) are being trapped. Thanks
Marcos for contributing this image!

Example:

Input: [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
 */

import java.util.Arrays;

public class TrappingRainWaterDp {

    public static void main(String[] args) {
        int[] input = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        TrappingRainWaterDp t = new TrappingRainWaterDp();
        int ans = t.trap(input);
        System.out.printf("%d <- %s\n", ans, Arrays.toString(input));
    }

    // 1ms, faster than 87.60% of Java online submissions
    public int trap(int[] height) {
        if (height == null || height.length <= 2) return 0;
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];
        int max = height[0];
        leftMax[0] = max;
        for (int i = 1; i < height.length; i++) {
            max = Math.max(max, height[i]);
            leftMax[i] = max;
        }
        max = height[height.length-1];
        rightMax[height.length-1] = max;
        for (int i = height.length-2; i >= 0; i--) {
            max = Math.max(max, height[i]);
            rightMax[i] = max;
        }
        max = 0;
        for (int i = 0; i < height.length; i++) {
            max += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return max;
    }

}
