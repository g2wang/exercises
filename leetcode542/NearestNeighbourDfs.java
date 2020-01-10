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

public class NearestNeighbourDfs {

    private static final int MAX = Integer.MAX_VALUE - 1;

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
        NearestNeighbourDfs nb = new NearestNeighbourDfs();
        int[][] a = nb.updateMatrix(m); 
        System.out.println("answer: -------");
        for (int[] r : a) {
            System.out.printf("%s\n", Arrays.toString(r));
        }
    }

    public int[][] updateMatrix(int[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                dp[i][j] = dfs(matrix, i, j, dp);
            }
        }
        return dp;
    }

    private int dfs(int[][] m, int i, int j, int[][] dp) {
        if (m[i][j] == 0) return 0;
        if (i-1 >= 0 && m[i-1][j] == 0) return 1; // top being 0
        if (j-1 >= 0 && m[i][j-1] == 0) return 1; // left being 0
        if (j+1 < m[0].length && m[i][j+1] == 0) return 1; // right being 0
        if (i+1 < m.length && m[i+1][j] == 0) return 1; // buttom being 0

        int min = MAX;

        // top-left region has at least one 0, which made dp values there known
        if (i-1 >= 0 && dp[i-1][j] != 0) {
            min = Math.min(min, dp[i-1][j]);
        }
        if (j-1 >= 0 && dp[i][j-1] != 0) {
            min = Math.min(min, dp[i][j-1]);
        }

        // top-left region has no 0's, recurse bottom-right
        if (j+1 < m[0].length) min = Math.min(min, dfs(m, i, j+1, dp));
        if (i+1 < m.length) min = Math.min(min, dfs(m, i+1, j, dp));
        return min + 1;
    }
    
}
