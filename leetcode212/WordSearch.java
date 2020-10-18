/**
 * 212. Word Search II
Hard:
Given a 2D board and a list of words from the dictionary, find all words in the board.

Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

Example:

Input:
board = [
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]
words = ["oath","pea","eat","rain"]

Output: ["eat","oath"]

Note:

All inputs are consist of lowercase letters a-z.
The values of words are distinct.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class WordSearch {

    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private static final char C0 = (char) 0;

    public static void main(String[] args) {
        char[][] board = {
            {'o','a','a','n'},
            {'e','t','a','e'},
            {'i','h','k','r'},
            {'i','f','l','v'}
        };
        String[] words = {"oath","pea","eat","rain"};
        WordSearch ws = new WordSearch();
        List<String> ans = ws.findWords(board, words);
        System.out.printf("ans: %s%n", ans);
    }

        public List<String> findWords(char[][] board, String[] words) {
        List<String> ans = new ArrayList<>(words.length);
        Node root = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, ans);
            }
        }
        return ans;
    }

    /**
     * Runtime: 8 ms, faster than 98.91% of Java online submissions for Word Search II.
     * Memory Usage: 48 MB, less than 64.32% of Java online submissions for Word Search II.
     */
    private void dfs(char[][] board, int i, int j,
                Node node, List<String> ans) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) return;
        final char c = board[i][j];
        if (c == C0) return;
        Node k = node.children.get(c);
        if (k == null) return;
        board[i][j] = C0;
        if (k.word != null) ans.add(readWord(k));
        for (int[] d : DIRECTIONS) {
            dfs(board, i+d[0], j+d[1], k, ans);
        }
        board[i][j] = c;
    }

    private String readWord(Node node) {
        String word = node.word;
        node.word = null;
        do {
            char removeChar = node.children.isEmpty()?node.val:C0;
            node = node.parent;
            if (removeChar != C0) node.children.remove(removeChar);
        } while (node.val != C0);
        return word;
    }

    private Node buildTrie(String[] words) {
        Node root = new Node(C0);
        for (String word : words) {
            buildSubTrie(word, root);
        }
        return root;
    }

    private void buildSubTrie(String word, Node node) {
        for (char c : word.toCharArray()) {
            Node k = node.children.get(c);
            if (k == null) {
                k = new Node(c);
                node.children.put(c, k);
                k.parent = node;
            }
            node = k;
        }
        node.word = word;
    }

    private class Node {
        char val;
        String word;
        Map<Character, Node> children = new HashMap<>();
        Node parent;
        public Node(char val) {
            this.val = val;
        }
    }
}
