/**
698. Partition to K Equal Sum Subsets
Medium

Given an integer array nums and an integer k, return true if it is possible to divide this array into k non-empty subsets whose sums are all equal.

Example 1:
Input: nums = [4,3,2,3,5,2,1], k = 4
Output: true
Explanation: It is possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.

Example 2:
Input: nums = [1,2,3,4], k = 3
Output: false
 
Constraints:
1 <= k <= nums.length <= 16
1 <= nums[i] <= 104
The frequency of each element is in the range [1, 4].
 *
 */
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class PartitionToKEqualSum {

    public static void main(String[] args) {
        PartitionToKEqualSum pk = new PartitionToKEqualSum();
        /*
        int[] nums = new int[]{4,3,2,3,5,2,1};
        int k = 4;
        */
        int[] nums = new int[]{2,2,2,2,3,4,5};
        int k = 4;

        boolean ans = pk.canPartitionKSubsets(nums, k);
        System.out.printf("(%s, %d) -> %b%n", Arrays.toString(nums), k, ans);
    }

    private int target;
    private int[] partitions;
    private int[] nums;


    /**
     * Runtime: 3 ms, faster than 97.07% of Java online submissions for Partition to K Equal Sum Subsets.
     * Memory Usage: 42.5 MB, less than 39.80% of Java online submissions for Partition to K Equal Sum Subsets.
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int total = 0;
        int max = Integer.MIN_VALUE;
        for (int n : nums) {
            total += n;
            max = Math.max(n, max);
        }
        if (total % k != 0) return false;
        this.target = total/k;
        if (max > target) return false;
        Arrays.sort(nums);
        this.partitions = new int[k];
        this.nums = nums;
        return canParti(nums.length-1);
    }

    private boolean canParti(int i) {
        if (i == -1) { // traversed the whole of nums from lastIndex
            return true;
        }
        for (int j = 0; j < partitions.length; j++) {
            if (nums[i] + partitions[j] <= target) {
                partitions[j] += nums[i];
                if (canParti(i-1)) return true;
                partitions[j] -= nums[i];
            }
            if (partitions[j] == 0) return false;
        }
        return false;
    }

}
