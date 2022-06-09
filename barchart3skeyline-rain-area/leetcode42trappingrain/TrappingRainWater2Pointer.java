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

public class TrappingRainWater2Pointer {

    public static void main(String[] args) {
        int[] input = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        TrappingRainWater2Pointer t = new TrappingRainWater2Pointer();
        int ans = t.trap(input);
        System.out.printf("%d <- %s\n", ans, Arrays.toString(input));
    }

    // 1 ms, faster than 87.75% of Java online submissions
    public int trap(int[] height) {
        if (height == null || height.length <= 2) return 0;
        int ans = 0, leftMax = 0, rightMax = 0;
        int leftIndex = 0, rightIndex = height.length-1;
        while (leftIndex < rightIndex) {
            if (height[leftIndex] < height[rightIndex]) {
                //we're sure that water trapped is determined by the left max
                if (leftMax < height[leftIndex]) {
                    leftMax = height[leftIndex];
                } else {
                    ans += leftMax - height[leftIndex];
                }
                leftIndex++;
            } else {
                //we're sure that water trapped is determined by the right max
                if (rightMax < height[rightIndex]) {
                    rightMax = height[rightIndex];
                } else {
                    ans += rightMax - height[rightIndex];
                }
                rightIndex--;
            }
        }
      
        return ans;
    }

}
