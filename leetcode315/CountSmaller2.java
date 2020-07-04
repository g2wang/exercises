/**
315. Count of Smaller Numbers After Self
Hard

You are given an integer array nums and you have to return a new counts array. The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].

Example:

Input: [5,2,6,1]
Output: [2,1,1,0] 
Explanation:
To the right of 5 there are 2 smaller elements (2 and 1).
To the right of 2 there is only 1 smaller element (1).
To the right of 6 there is 1 smaller element (1).
To the right of 1 there is 0 smaller element. 
 */

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class CountSmaller2 {

    public static void main(String[] args) {
        int[] input = {
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80,
            10, 2, 5, 101, 6, 100 ,2, 6, 105, 6, 30, 5, 250, 11, 93, 6, 100, 2, 5, 6, 6, 99, 80
        };         
        CountSmaller2 cs = new CountSmaller2();
        long start = System.currentTimeMillis();
        List<Integer> output = cs.countSmaller(input);
        long elapse = System.currentTimeMillis() - start;
        System.out.printf("output: %s%n", output);
        System.out.printf("elapse time: %d%n", elapse);
    }

    /**
     * copyright: the author of a LeetCode submission
     */
    public List<Integer> countSmaller(int[] nums) {
        if(nums.length==0) return Collections.emptyList();
        Integer[] ret = new Integer[nums.length];

        Node head = new Node(nums[nums.length-1]);
        ret[nums.length-1] = 0;
        for(int i = nums.length - 2; i >= 0; i--) {
            ret[i] = insert(head, nums[i], 0);
        }
        return Arrays.asList(ret);
    }

    private int insert(Node node, int num, int less) {

        if(node.val == num) {

            node.count++;
            less += node.less;

        } else if(node.val < num) {

            less += node.count + node.less;
            if(node.right==null) {
                node.right = new Node(num);
            } else {
                less = insert(node.right, num, less);
            }
        } else {

            node.less++;
            if(node.left==null) {
                node.left = new Node(num);
            } else {
                less = insert(node.left, num, less);
            }
        }
        return less;
    }

    static class Node {
        int val;
        int count;
        int less;
        Node left;
        Node right;

        Node(int val) {
            count = 1;
            this.val = val;
        }

        public String toString() {
            return "["+val+", "+count+", "+less+", "+left+", "+right+"]";
        }
    }

}
