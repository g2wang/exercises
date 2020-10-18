/**
239. Sliding Window Maximum

You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

Return the max sliding window.

Example 1:
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation:
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7

Example 2:
Input: nums = [1], k = 1
Output: [1]

Example 3:
Input: nums = [1,-1], k = 1
Output: [1,-1]

Example 4:
Input: nums = [9,11], k = 2
Output: [11]

Example 5:
Input: nums = [4,-2], k = 2
Output: [4]

Example 6:
Input: nums = [-7,-8,7,5,7,1,6,0], k = 4
Output: [7,7,7,7,7]

Constraints:

1 <= nums.length <= 105
-104 <= nums[i] <= 104
1 <= k <= nums.length
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

public class SlidingWindowMax3 {

    public static void main(String[] args) {
        int[] nums = new int[]{-7,-8,7,5,7,1,6,0};
        int k = 4;
        SlidingWindowMax3 swm = new SlidingWindowMax3();
        int[] ans = swm.maxSlidingWindow(nums, k);
        System.out.printf("ans: %s%n", Arrays.toString(ans));
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if(n * k == 0) return new int[0];
        if(k ==1) return nums;
        int left[] = new int[n];
        int right[]= new int[n];
        left[0] = nums[0];
        right[n-1] = nums[n-1];

        for (int i = 1; i < n; i++) {
            //modifying the left array
            if (i%k == 0) //Checking if i is the start of any block
                left[i] = nums[i];
            else
                left[i] = Math.max(left[i-1], nums[i]);

            //modifying the right array
            int j = n-i-1;
            if ((j+1)%k == 0)
                right[j] = nums[j];
            else
                right[j] = Math.max(right[j+1], nums[j]);
        }

        int[] output = new int[n-k+1];

        for (int i = 0; i < n-k+1; i++) {
            output[i] = Math.max(left[i+k-1], right[i]);
        }

        return output;

    }

    public int[] maxSlidingWindowFast(int[] nums, int k) {
        if (k == 1) return nums;
        int n = nums.length;
        int[] result = new int[n-k+1];
        int[] left = new int[n];
        int[] right = new int[n];

        for (int d = 0; d <= n/k; d++) {
            int max = Integer.MIN_VALUE;
            for (int i = d*k; i < (d+1)*k && i < n; i++) {
                max = Math.max(max, nums[i]);
                left[i] = max;
            }

            max = Integer.MIN_VALUE;
            for (int i = Math.min(n-1, (d+1)*k-1); i >= d*k; i--) {
                max = Math.max(max, nums[i]);
                right[i] = max;
            }
        }

        for (int i = 0; i+k <= n; i++) {
            int j = i+k-1;
            result[i] = Math.max(right[i], left[j]);
        }

        return result;
    }

}
