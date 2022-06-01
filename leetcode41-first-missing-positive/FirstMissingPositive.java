/**
*41. First Missing Positive
Hard

Given an unsorted integer array nums, return the smallest missing positive integer.

You must implement an algorithm that runs in O(n) time and uses constant extra space.
 

Example 1:
Input: nums = [1,2,0]
Output: 3

Example 2:
Input: nums = [3,4,-1,1]
Output: 2

Example 3:
Input: nums = [7,8,9,11,12]
Output: 1

Constraints:

1 <= nums.length <= 5 * 105
-231 <= nums[i] <= 231 - 1
 */
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class FirstMissingPositive {

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{3,4,-1,1});
        list.add(new int[]{7,8,9,11,12});
        list.add(new int[]{1,2,3});
        list.add(new int[]{1,0,0});
        list.add(new int[]{-1});
        list.add(new int[]{1});
        list.add(new int[]{0});
        list.add(new int[]{1,0,2});
        list.add(new int[]{1,1,1});
        FirstMissingPositive fmp = new FirstMissingPositive();
        for (int[] nums : list) {
            System.out.printf("%s -> ", Arrays.toString(nums));
            int result = fmp.firstMissingPositive(nums);
            System.out.printf("%d%n", result);
        }
    }

    public int firstMissingPositive(int[] a) {
        if (a == null || a.length == 0) return 1;
        int n = a.length;
        boolean missing1 = true;
        for (int i = 0; i < n; i++) {
            if (a[i] == 1) {
                missing1 = false;
            } else if (a[i] <= 0 || a[i] > n){
                a[i] = 1;
            }
        }
        if (missing1) return 1;
        for (int i = 0; i < n; i++) {
            int idx = Math.abs(a[i]) - 1;
            if (a[idx] > 0) a[idx] = - a[idx];
        }
        for (int i = 0; i < n; i++) {
            if (a[i] > 0) return i+1;
        }
        return n+1;
    }
}
