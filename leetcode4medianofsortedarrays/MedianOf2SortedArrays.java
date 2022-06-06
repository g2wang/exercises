/**
4. Median of Two Sorted Arrays
Hard

Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.

The overall run time complexity should be O(log(m+n)).

Example 1:
Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.

Example 2:
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.

Constraints:

nums1.length == m
nums2.length == n
0 <= m <= 1000
0 <= n <= 1000
1 <= m + n <= 2000
-106 <= nums1[i], nums2[i] <= 106
 */
import java.util.Arrays;

public class MedianOf2SortedArrays {
    public static void main(String[] args) {
        int[] nums1 = new int[]{-1};
        int[] nums2 = new int[]{-1};
        MedianOf2SortedArrays m = new MedianOf2SortedArrays();
        double ans = m.findMedianSortedArrays(nums1, nums2);
        System.out.printf("%s, %s -> %f%n", Arrays.toString(nums1), Arrays.toString(nums2), ans);
    }

    /**
     * partition a and b such that 
     *  if (len(a) + len(b)) % 2 == 0
     *   left(a) + left(b) = right(a) + right(b) 
     *  else 
     *   left(a) + left(b) = right(a) + right(b) + 1
     * and union(left(a), left(b)) <= union(right(a), right(b)) 
     * to ensure O(log(m+n)) complexity, 
     *  use binary search to find the parition index of the shorter of a and b.
     *  Then find the partion index of the other.
     */
    public double findMedianSortedArrays(int[] a, int[] b) {
        double ans;
        if ((a == null || a.length == 0) && (b == null || b.length == 0)) {
            throw new IllegalArgumentException("arrays cannot be both empty");
        }
        if (a == null || a.length == 0) {
            return median(b);
        }
        if (b == null || b.length == 0) {
            return median(a);
        }

        int lenA = a.length, lenB = b.length;
        if (lenA <= lenB) {
            ans = searchInOrder(a, b);
        } else {
            ans = searchInOrder(b, a);
        }
        return ans;
    }

    /**
     * a is shorter than b
     */
    private double searchInOrder(int[] a, int[] b) {
        int len = a.length + b.length;
        int lenL = len/2;
        if (len % 2 != 0) {
            lenL++;
        }
        int lA, rA, lB, rB; 
        int l = 0, r = a.length - 1;
        while (true) {
            rA = l + (r-l)/2;
            lA = rA - 1;
            rB = lenL - rA;
            lB = rB -1;
            // System.out.printf("lA:%d, rA:%d, lB:%d, rB:%d%n", lA, rA, lB, rB);
            int ml, mr;
            if (isValidIndex(lA, a) && isValidIndex(lB, b)) {
                ml = Math.max(a[lA], b[lB]);
            } else if (isValidIndex(lA, a)) {
                ml = a[lA];
            } else {
                ml = b[lB];
            }
            if (isValidIndex(rA, a) && isValidIndex(rB, b)) {
                mr = Math.min(a[rA], b[rB]);
            } else if (isValidIndex(rA, a)) {
                mr = a[rA];
            } else {
                mr = b[rB];
            }
            if (ml <= mr) {
              return len%2 == 0? (ml + mr)/2.0 : ml;
            } else if (isValidIndex(lA, a) && a[lA] > b[rB]) {
                r = rA - 1;
            } else {
                l = rA + 1;
            }
        }
    }

    boolean isValidIndex(int i, int[] a) {
        return i >= 0 && i < a.length;
    }

    private double median(int[] a) {
        int n = a.length;
        if (n % 2 == 0) return (a[n/2-1]+a[n/2])/2.0; 
        return a[n/2];
    }

}
