/**
 * given a matrix m[n][n], traverse the upper right triangle of it diagonal.
 */

public class DiagonalTraverse {

    public static void main(String[] args) {
        int n = 4;
        int[][] m = new int[n][n];
        DiagonalTraverse dt = new DiagonalTraverse();
        dt.traverse(m);
    }

    public void traverse(int[][] m) {
        int n = m.length;
        // g: the gap between i and j
        for (int g = 0; g < n; g++) {
            for (int i = 0; i < n-g; i++) {
                visit(m, i, i+g);
            }
            System.out.println("");
        }
    }

    public void visit(int[][] m, int i, int j) {
        System.out.printf("(%d, %d),", i, j);
    }

}
