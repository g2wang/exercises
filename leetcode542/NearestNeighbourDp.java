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

public class NearestNeighbourDp {

    private static final int MAX = Integer.MAX_VALUE - 10000;

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
        NearestNeighbourDp nb = new NearestNeighbourDp();
        int[][] a = nb.updateMatrix(m); 
        System.out.println("answer: -------");
        for (int[] r : a) {
            System.out.printf("%s\n", Arrays.toString(r));
        }
    }


    public int[][] updateMatrix(int[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int[] r : dp) {
            Arrays.fill(r, MAX);
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                } else {
                    if (i-1 >= 0) dp[i][j] = Math.min(dp[i][j], dp[i-1][j]+1);
                    if (j-1 >= 0) dp[i][j] = Math.min(dp[i][j], dp[i][j-1]+1);
                }
            }
        }
        for (int i = matrix.length-1; i >= 0; i--) {
            for (int j = matrix[0].length-1; j >= 0; j--) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                } else {
                    if (i+1 < matrix.length)
                        dp[i][j] = Math.min(dp[i][j], dp[i+1][j]+1);
                    if (j+1 < matrix[0].length)
                        dp[i][j] = Math.min(dp[i][j], dp[i][j+1]+1);
                }
            }
        }
        return dp;
    }
    
}
