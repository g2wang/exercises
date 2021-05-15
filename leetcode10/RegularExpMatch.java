/**
10. Regular Expression Matching
Hard

Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*' where:

'.' Matches any single character.
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).

Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".

Example 2:

Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".

Example 3:

Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".

Example 4:

Input: s = "aab", p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".

Example 5:

Input: s = "mississippi", p = "mis*is*p*."
Output: false

Constraints:

0 <= s.length <= 20
0 <= p.length <= 30
s contains only lowercase English letters.
p contains only lowercase English letters, '.', and '*'.
It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class RegularExpMatch {

    private int seq = 0;
    private Map<Integer, Map<Character, Integer>> transitionTable = new HashMap<>();
    private Set<Integer> acceptStates = new HashSet<>();

    public static void main(String[] args) {
        String s = "aaa";
        String p = "a*";
        RegularExpMatch r = new RegularExpMatch();
        boolean matches = r.isMatch(s, p);
        System.out.printf("%s matches %s: %b%n", s, p, matches);
    }

    public boolean isMatch(String s, String p) {
        Integer state = Integer.valueOf(0);
        buildTransitionTable(state, p, 0);
        return match(state, s);
    }

    public boolean match(Integer state, String s) {
        if (state == null) return false;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            Map<Character, Integer> transitions = transitionTable.get(state);
            if (transitions == null) return false;
            state = transitions.get('.');
            if (state == null) {
                state = transitions.get(c);
            } else {
                if (match(transitions.get(c), s.substring(i+1))) return true;
            }
            if (state == null) return false;
        }
        if (acceptStates.contains(state)) return true;
        return false;
    }

    private void buildTransitionTable(int state, String p, int index) {
        if (index == p.length()) {
            acceptStates.add(state);
            return;
        }
        Map<Character, Integer> transitions = transitionTable.get(state);
        if (transitions == null) {
            transitions = new HashMap<>();
            transitionTable.put(state, transitions);
        }
        Integer nextState;
        char c = p.charAt(index);
        switch (c) {
            case '*':
                buildTransitionTable(state-1, p, index+1);
                transitions.put(p.charAt(index-1), state);
                buildTransitionTable(state, p, index+1);
                break;
            default:
                nextState = ++seq;
                transitions.put(c, nextState);
                buildTransitionTable(nextState, p, index+1);
                break;
        }

    }

}
