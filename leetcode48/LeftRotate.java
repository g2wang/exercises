/**

-48. Rotate Image -- anti-clockwise
Medium

You are given an n x n 2D matrix representing an image.

Rotate the image by 90 degrees (anti-clockwise).

Note:

You have to rotate the image in-place, which means you have to modify the input
2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.

Example 1:

Given input matrix =
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
],

rotate the input matrix in-place such that it becomes:
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
]


Example 2:

Given input matrix =
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
],

rotate the input matrix in-place such that it becomes:
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
]
*/

import java.util.Arrays;

public class LeftRotate {

    public static void main(String[] args) {
        int[][] m = new int[][]{
            {7,4,1},
            {8,5,2},
            {9,6,3}
        };
        LeftRotate ri = new LeftRotate();
        ri.rotate(m);
        for (int[] r : m) {
            System.out.printf("%s%n", Arrays.toString(r));
        }
    }

    public void rotate(int[][] matrix) {
        int w = matrix.length;
        for (int j = 0; j < w/2; j++) {
            int r = w-j-1;
            for (int i = 0; i < w; i++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][r];
                matrix[i][r] = tmp;
            }
        }

        for (int i = 0; i < w; i++) {
            for (int j = i+1; j < w; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }

}
