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

public class WordSearch2 {

    private static int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public static void main(String[] args) {
        char[][] board = {
            {'o','a','a','n'},
            {'e','t','a','e'},
            {'i','h','k','r'},
            {'i','f','l','v'} 
        };
        String[] words = {"oath","pea","eat","rain"};
        WordSearch2 ws = new WordSearch2();
        List<String> ans = ws.findWords(board, words); 
        System.out.printf("ans: %s%n", ans);
    }

    public List<String> findWords(char[][] board, String[] words) {
        final TrieNode root = new TrieNode();
        for (String word : words) {
            root.add(word, 0);
        }

        final List<String> out = new ArrayList<>();
        final int R = board.length;
        final int C = board[0].length;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                find(board, r, c, root, out);
                if (root.size == 0) return out;
            }
        }

        return out;
    }

    private static int find(char[][] board, int r, int c, TrieNode node, List<String> out) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return 0;
        char ch = board[r][c];
        if (ch == '#') return 0;
        TrieNode next = node.next(ch);
        if (next == null) return 0;
        board[r][c] = '#';
        int less = 0;
        less += find(board, r+1, c, next, out);
        less += find(board, r-1, c, next, out);
        less += find(board, r, c+1, next, out);
        less += find(board, r, c-1, next, out);
        String word = next.word;
        if (word != null) {
            out.add(word);
            next.word = null;
            less += 1;
        }
        if ((next.size -= less) == 0) {
            node.remove(ch);
        }
        board[r][c] = ch;
        return less;
    }

    private static class TrieNode {
        private TrieNode children[];
        private String word;
        private int size;

        private void add(String w, int i) {
            if (w.length() == i) {
                word = w;
            } else {
                if (children == null) {
                    children = new TrieNode[26];
                }
                int cIdx = w.charAt(i) - 'a';
                TrieNode child = children[cIdx];
                if (child == null) {
                    child = new TrieNode();
                    children[cIdx] = child;
                }
                child.add(w, i+1);
            }
            size += 1;
        }

        private TrieNode next(char c) {
            return children == null ? null : children[c - 'a'];
        }

        private TrieNode remove(char c) {
            return children[c - 'a'] = null;
        }
    }
}

