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
import java.util.ArrayDeque;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class NearestNeighbour {

    public static void main(String[] args) {
        int[][] m = new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {1, 1, 1}
        };
        for (int[] r : m) {
            System.out.printf("%s\n", Arrays.toString(r));
        }
        NearestNeighbour nb = new NearestNeighbour();
        int[][] a = nb.updateMatrix(m); 
        System.out.println("answer: -------");
        for (int[] r : a) {
            System.out.printf("%s\n", Arrays.toString(r));
        }
    }

    public int[][] updateMatrix(int[][] matrix) {
        int[][] ans = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int[] p = new int[]{i, j};
                bfs(matrix, p, ans);
            }
        }
        return ans;
    }

    private void bfs(int[][] m, int[] p0, int[][] ans) {
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(p0);
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] p = queue.poll();
                if (m[p[0]][p[1]] == 0) {
                    ans[p0[0]][p0[1]] = depth;
                    return;
                }
                if (p[0]-1 >= 0) {
                    int[] up = new int[]{p[0]-1, p[1]};
                    queue.offer(up);
                }
                if (p[0]+1 < m.length) {
                    int[] down = new int[]{p[0]+1, p[1]};
                    queue.offer(down);
                }
                if (p[1]-1 >= 0) {
                    int[] left = new int[]{p[0], p[1]-1};
                    queue.offer(left);
                }
                if (p[1]+1 < m[0].length) {
                    int[] right = new int[]{p[0], p[1]+1};
                    queue.offer(right);
                }
            }
            depth++;
        }
    }

}
