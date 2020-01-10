/**
 542. 01 Matrix
Medium

Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.

The distance between two adjacent cells is 1.
 
Example 1:

Input:
[[0,0,0],
 [0,1,0],
 [0,0,0]]

Output:
[[0,0,0],
 [0,1,0],
 [0,0,0]]

Example 2:

Input:
[[0,0,0],
 [0,1,0],
 [1,1,1]]

Output:
[[0,0,0],
 [0,1,0],
 [1,2,1]]
 
Test case:

Input:
[[0,1,0],
 [0,1,0],
 [0,1,0],
 [0,1,0],
 [0,1,0]]

Output:
[[0,1,0],
 [0,1,0],
 [0,1,0],
 [0,1,0],
 [0,1,0]]

Test case:
Input:
[[1,1,0,0,1,0,0,1,1,0],
 [1,0,0,1,0,1,1,1,1,1],
 [1,1,1,0,0,1,1,1,1,0],
 [0,1,1,1,0,1,1,1,1,1],
 [0,0,1,1,1,1,1,1,1,0],
 [1,1,1,1,1,1,0,1,1,1],
 [0,1,1,1,1,1,1,0,0,1],
 [1,1,1,1,1,0,0,1,1,1],
 [0,1,0,1,1,0,1,1,1,1],
 [1,1,1,0,1,0,1,1,1,1]]

Output:

[[2,1,0,0,1,0,0,1,1,0],
 [1,0,0,1,0,1,1,2,2,1],
 [1,1,1,0,0,1,2,2,1,0],
 [0,1,2,1,0,1,2,3,2,1],
 [0,0,1,2,1,2,1,2,1,0],
 [1,1,2,3,2,1,0,1,1,1],
 [0,1,2,3,2,1,1,0,0,1],
 [1,2,1,2,1,0,0,1,1,2],
 [0,1,0,1,1,0,1,2,2,3],
 [1,2,1,0,1,0,1,2,3,4]]

Note:

The number of elements of the given matrix will not exceed 10,000.
There are at least one 0 in the given matrix.
The cells are adjacent in only four directions: up, down, left and right.
 */

import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

public class NearestNeighbourBfsStandard {

    public static void main(String[] args) {
//        int[][] m = new int[][]{
//            {0, 0, 0},
//            {0, 1, 0},
//            {1, 1, 1}
//        };

        int[][] m = new int[][]{
            {0,0,1,0,1,1,1,0,1,1},
            {1,1,1,1,0,1,1,1,1,1},
            {1,1,1,1,1,0,0,0,1,1},
            {1,0,1,0,1,1,1,0,1,1},
            {0,0,1,1,1,0,1,1,1,1},
            {1,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,0,1,0,1,0,1},
            {0,1,0,0,0,1,0,0,1,1},
            {1,1,1,0,1,1,0,1,0,1},
            {1,0,1,1,1,0,1,1,1,0}
        };

//        int[][] m = new int[][]{
//            {0,1,0},
//            {0,1,0},
//            {0,1,0},
//            {0,1,0},
//            {0,1,0}
//        };

        for (int[] r : m) {
            System.out.printf("%s\n", Arrays.toString(r));
        }
        NearestNeighbourBfsStandard nb = new NearestNeighbourBfsStandard();
        int[][] a = nb.updateMatrix(m); 
        System.out.println("answer: -------");
        for (int[] r : a) {
            System.out.printf("%s\n", Arrays.toString(r));
        }
    }

    public int[][] updateMatrix(int[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int[] r : dp) {
            Arrays.fill(r, Integer.MAX_VALUE);
        }
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                    q.offer(new int[]{i, j});
                }
            }
        }
        int[][] dirs = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        while (!q.isEmpty()) {
            int[] p = q.poll();
            int i0 = p[0], j0 = p[1];
            for (int[] d : dirs) {
                int i = p[0] + d[0], j = p[1] + d[1];
                if (i >= 0 && j >= 0 && i < matrix.length && j < matrix[0].length) {
                    int newVal = dp[i0][j0]+1;
                    if (newVal < dp[i][j]) {
                        dp[i][j] = newVal;
                        q.offer(new int[]{i, j});
                    }
                }
            }
        }
        return dp;
    }

}
