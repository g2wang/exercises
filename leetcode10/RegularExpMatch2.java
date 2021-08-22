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
import java.util.List;
import java.util.ArrayList;

public class RegularExpMatch2 {

    private int seq = 0;
    private static final char NONE = 0;
    private Map<Integer, Map<Character, List<Integer>>> transitionTable = new HashMap<>();
    private Set<Integer> acceptStates = new HashSet<>();
    private Map<String, Boolean> cache = new HashMap<>();

    public static void main(String[] args) {
        // String s = "abcaaaaaaabaabcabac";
        // String p = ".*ab.a.*a*a*.*b*b*";
        // String p = ".*ab.a.*a*.*b*";
        // ans = true

        // String s = "aaa";
        // String p = "a*a";

        // String s = "a";
        // String p = "ab*";
        // ans true

        // String s = "ccacbcbcccabbab";
        // String p = ".c*a*aa*b*.*b*.*";
        // ans = true

        String s = "aaaaaaaaaaaaab";
        String p = "a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*c";
        // String p = "a*b*c*d*e*f*g*h*i*j*k*l*m*n*o*p*q*r*s*t*u*v*c";

        RegularExpMatch2 r = new RegularExpMatch2();
        boolean matches = r.isMatch(s, p);
        System.out.printf("%s matches %s: %b%n", s, p, matches);
    }

    public boolean isMatch(String s, String p) {
        Integer state = Integer.valueOf(0);
        System.out.printf("pattern           : %s%n", p);
        p = normalize(0, p);
        System.out.printf("normalized pattern: %s%n", p);
        buildTransitionTable(state, p, 0);
        System.out.printf("transitionTable: %s%n", transitionTable);
        System.out.printf("acceptStates: %s%n", acceptStates);
        return match(state, s);
    }

    private Boolean matchCache(Integer t, String s) {
        String key = t + "\t" + s;
        Boolean mt = cache.get(key);
        if (mt == null) {
            mt = match(t, s);
            cache.put(key, mt);
        }
        return mt;
    }

    public boolean match(Integer state, String s) {
        if (s.isEmpty()) {
            if (acceptStates.contains(state)) return true;
            return false;
        }
        Map<Character, List<Integer>> transitions = transitionTable.get(state);
        if (transitions == null) return false;
        List<Integer> states = transitions.get(NONE);
        if (states != null && !states.isEmpty()) {
            for (Integer t : states) {
                if (matchCache(t, s)) return true;
            }
        }
        states = transitions.get('.');
        if (states != null && !states.isEmpty()) {
            for (Integer t : states) {
                if (matchCache(t, s.substring(1))) return true;
            }
        }
        states = transitions.get(s.charAt(0));
        if (states != null && !states.isEmpty()) {
            for (Integer t : states) {
                if (matchCache(t, s.substring(1))) return true;
            }
        }
        return false;
    }

    private void expandAcceptStates(Integer state) {
        if (state == 0) return;
        Integer prevState = state - 1;
        Map<Character, List<Integer>> transitions = transitionTable.get(prevState); 
        List<Integer> states = transitions.get(NONE);
        if (states != null && !states.isEmpty()) {
            for (Integer st : states) {
                if (st.equals(state)) {
                    acceptStates.add(prevState);
                    expandAcceptStates(prevState);
                }
            }
        }
    }

    private void buildTransitionTable(Integer state, String p, int index) {
        if (index == p.length()) {
            acceptStates.add(state);
            expandAcceptStates(state);
            return;
        }
        Map<Character, List<Integer>> transitions = transitionTable.get(state);
        if (transitions == null) {
            transitions = new HashMap<>();
            transitionTable.put(state, transitions);
        }
        Integer nextState;
        char c = p.charAt(index);
        Map<Character, List<Integer>> prevTransitions;
        List<Integer> states; 
        switch (c) {
            case '*':
                prevTransitions = transitionTable.get(state-1);
                states = prevTransitions.get(NONE);
                if (states == null) {
                    states = new ArrayList<>();
                    prevTransitions.put(NONE, states);
                }
                states.add(state);
                states = transitions.get(p.charAt(index-1));
                if (states == null) {
                    states = new ArrayList<>();
                    transitions.put(p.charAt(index-1), states);
                }
                states.add(state);
                buildTransitionTable(state, p, index+1);
                break;
            default:
                nextState = ++seq;
                states = transitions.get(c);
                if (states == null) {
                    states = new ArrayList<>();
                    transitions.put(c, states);
                }
                states.add(nextState);
                buildTransitionTable(nextState, p, index+1);
                break;
        }

    }

    private String normalize(int startIndex, String p) {
        char[] chars = p.toCharArray();
        for (int i = startIndex; i < chars.length; i++) {
            if (chars[i] == '*' && i+2 < chars.length) {
                if (chars[i-1] == chars[i+1] && chars[i+2] == '*') {
                    return normalize(i-1, p.substring(0, i+1) + p.substring(i+3));
                }
            }
        }
        return p;
    }

}
