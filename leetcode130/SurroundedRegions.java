/**
 * LeetCode 130:
 *
 * Given an m x n matrix board containing 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X'.
    A region is captured by flipping all 'O's into 'X's in that surrounded region.

    Example 1:
    Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
    Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
    Explanation: Surrounded regions should not be on the border, which means that any 'O' on the border of the board are not flipped to 'X'.
    Any 'O' that is not on the border and it is not connected to an 'O' on the border will be flipped to 'X'. 
    Two cells are connected if they are adjacent cells connected horizontally or vertically.

    Example 2:
    Input: board = [["X"]]
    Output: [["X"]]
 *
 */
public class SurroundedRegions {

    public static void main(String[] args) {



    }


    public void capture(char[][] board) {
        int r = board.length;
        int c = board[0].length;
        for (int i = 0; i < r; i++) {
            dfs(i, 0, board); 
            dfs(i, c-1, board); 
        }
        for (int j = 0; j < c; j++) {
            dfs(0, j, board); 
            dfs(r-1, j, board); 
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == '*') {
                    board[i][j] = 'O';
                }
            }
        }
    }
    
    private void dfs(int i, int j, char[][] board) {
        int r = board.length;
        int c = board[0].length;
        if (i < 0 || j < 0 || i >= r || j >= c || board[i][j] != 'O') {
            return;
        } 
        board[i][j] = '*';
        dfs(i, j+1, board);
        dfs(i, j-1, board);
        dfs(i+1, j, board);
        dfs(i-1, j, board);
    }
}
