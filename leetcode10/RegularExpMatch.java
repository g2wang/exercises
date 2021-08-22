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
    private static final char NONE = '0';
    
    private Map<Integer, Map<Character, Integer>> transitionTable = new HashMap<>(); 
    private Set<Integer> acceptStates = new HashSet<>();
    private Map<String, Boolean> cache = new HashMap<>(); 

    public static void main(String[] args) {
        // String s = "aaeeeeeeeeeeeea";
        // String p = "ab*a*.*d*e*f*.*g*.*h*.*c*a";
        // String s = "aaa";
        // String p = "a*a";
        // String s = "aaab";
        // String p = "a*a*b";
        String s = "ccacbcbcccabbab";
        String p = ".c*a*aa*b*.*b*.*";
        // ans = true
        RegularExpMatch r = new RegularExpMatch();
        boolean matches = r.isMatch(s, p);
        System.out.printf("%s matches %s: %b%n", s, p, matches);
    }

    public boolean isMatch(String s, String p) {
        p = normalize(p);
        buildTransitionTable(0, p, 0);
        return match(0, s);
    }
    
    private Boolean matchCache(Integer state, String s) {
        String key = state + "\t" + s;
        Boolean result = cache.get(key);
        if (result == null) {
            result = match(state, s); 
            cache.put(key, result);
        }
        return result;
    }
    
    public boolean match(Integer state, String s) {
        if (s.isEmpty()) {
            if (acceptStates.contains(state)) return true;
            return false;   
        }
        Map<Character, Integer> transitions = transitionTable.get(state);
        if (transitions == null) return false;
        Integer st = transitions.get(NONE); 
        if (st != null && matchCache(st, s)) return true;
        
        st = transitions.get('.');
        if (st != null && matchCache(st, s.substring(1))) return true;
        
        st = transitions.get(s.charAt(0));    
        if (st != null && matchCache(st, s.substring(1))) return true;
        return false; 
    }
    
    private void expandAcceptStates(Integer state) {
        if (state == 0) return;
        Integer prevState = state - 1;
        Map<Character, Integer> transitions = transitionTable.get(prevState);
        Integer st = transitions.get(NONE);
        if (st != null && st.equals(state)) {
            acceptStates.add(prevState); 
            expandAcceptStates(prevState);
        }
    }
    
    private void buildTransitionTable(int state, String p, int index) {
        if (index == p.length()) {
            acceptStates.add(state); 
            expandAcceptStates(state);
            return;
        }
        Map<Character, Integer> transitions = 
            transitionTable.computeIfAbsent(state, k -> new HashMap<>());

        Integer nextState;
        char c = p.charAt(index);
        Map<Character, Integer> prevTransitions;
        switch (c) {
            case '*':
                prevTransitions = transitionTable.get(state-1);
                prevTransitions.put(NONE, state);   
                
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
    
    private String normalize(String p) {
        String p1 = p;
        do {
            p = p1;
            String p0 = normalize0(0, p);
            p1 = normalize1(p0);
        } while (!p1.equals(p));
        return p1;
    }

    private String normalize0(int startIndex, String p) {
        char[] cs = p.toCharArray();
        int len = cs.length;
        for (int i = startIndex; i < len; i++) {
            if (cs[i] == '*' && i+2 < len && cs[i-1] == cs[i+1] && cs[i+2] == '*') {
                return normalize0(i-1, p.substring(0, i+1) + p.substring(i+3));
            }
        }
        return p;
    }
    
    private String normalize1(String p) {
        char[] cs = p.toCharArray();
        for (int i = 2; i < cs.length; i++) {
            if (cs[i-1] == '*' && cs[i-2] == cs[i]) {
                char tmp = cs[i];
                cs[i] = cs[i-1];
                cs[i-1] = tmp;
                i++;
            }
        }
        return new String(cs);
    }

}
