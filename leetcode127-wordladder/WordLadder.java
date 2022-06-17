/**
127. Word Ladder
Hard

A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.

Example 1:
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.

Example 2:
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.

Constraints:
1 <= beginWord.length <= 10
endWord.length == beginWord.length
1 <= wordList.length <= 5000
wordList[i].length == beginWord.length
beginWord, endWord, and wordList[i] consist of lowercase English letters.
beginWord != endWord
All the words in wordList are unique.
 */
import java.util.*;

public class WordLadder {
    public static void main(String[] args) {
        String beginWord = "hit", endWord = "cog"; 
        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog");
        WordLadder wl = new WordLadder();
        int ans = wl.ladderLength(beginWord, endWord, wordList);
        System.out.printf("beginWord: %s, endWord: %s, wordList: %s -> %d%n", beginWord, endWord, wordList, ans);
    } 

    private void buildMap(String word, Map<String, List<String>> map) {
        for (int i = 0; i < word.length(); i++) {
            String pattern = word.substring(0, i) + "*" + word.substring(i+1); 
            List<String> neighbors = map.get(pattern);
            if (neighbors == null) {
                neighbors = new ArrayList<>();
                map.put(pattern, neighbors);
            }
            neighbors.add(word);
        }
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (beginWord.length() == 1) return 2;
        Map<String, List<String>> map = new HashMap<>();
        buildMap(beginWord, map);
        for (String word : wordList) {
            buildMap(word, map);
        }

        Set<String> visited = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        q.offer(beginWord);
        visited.add(beginWord);
        Map<String, String> parent = new HashMap<>();
        while (!q.isEmpty()) {
            String w = q.poll();
            if (w.equals(endWord)) {
                Deque<String> stack = new ArrayDeque<>();
                stack.push(w);
                String p = parent.get(w);
                while (p != null) {
                    stack.push(p);
                    p = parent.get(p);
                }
                int len = stack.size();
                /*
                System.out.printf("%s", stack.pop());
                while (!stack.isEmpty()) {
                    System.out.printf(" -> %s", stack.pop());
                }
                System.out.println("");
                */
                return len;
            }
            for (int i = 0; i < w.length(); i++) {
                String pattern = w.substring(0,i) + "*" + w.substring(i+1); 
                List<String> neighbors = map.get(pattern);
                for (String nb : neighbors) {
                    if (!visited.contains(nb)) {
                        q.offer(nb);
                        visited.add(nb);
                        // System.out.printf("%s <- %s%n", nb, w);
                        parent.put(nb, w);
                    }
                }
            }
        }
        return 0;
    }


}
