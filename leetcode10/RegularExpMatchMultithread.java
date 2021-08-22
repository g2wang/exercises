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
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;

/**
 * slow because of thread pool in recursion
 */
public class RegularExpMatchMultithread {

    private int seq = 0;
    private static final char NONE = 0;
    private Map<Integer, Map<Character, Integer>> transitionTable = new HashMap<>();
    private Set<Integer> acceptStates = new HashSet<>();

    public static void main(String[] args) {
        String s = "aaeeeeeeeeeeeea";
        String p = "ab*a*.*d*e*f*.*g*.*h*.*c*a";
        RegularExpMatchMultithread r = new RegularExpMatchMultithread();
        boolean matches = r.isMatch(s, p);
        System.out.printf("%s matches %s: %b%n", s, p, matches);
    }

    public boolean isMatch(String s, String p) {
        Integer state = Integer.valueOf(0);
        p = normalize(p);
        buildTransitionTable(state, p, 0);
        System.out.printf("transitionTable: %s%n", transitionTable);
        System.out.printf("acceptStates: %s%n", acceptStates);
        return match(state, s);
    }

    public boolean match(Integer state, String s) {
        if (state == null) return false;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int n = 2;
            Collection<Callable<Boolean>> solvers = new ArrayList<>(n);
            char c = chars[i];
            Map<Character, Integer> transitions = transitionTable.get(state);
            if (transitions == null) return false;
            state = transitions.get(NONE);
            if (state != null) {
                final int fstate = state;
                final String fstr = s.substring(i);
                solvers.add(()-> match(fstate, fstr));
            }
            state = transitions.get('.');
            if (state != null) {
                final int fstate = state;
                final String fstr = s.substring(i+1);
                solvers.add(()-> match(fstate, fstr));
            }

            if (!solvers.isEmpty()) {
                ExecutorService pool = Executors.newFixedThreadPool(n);
                CompletionService<Boolean> ecs = new ExecutorCompletionService<Boolean>(pool);
                List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>(n);
                try {
                    for (Callable<Boolean> solver : solvers) futures.add(ecs.submit(solver));
                    for (int j = 0; j < solvers.size(); ++j) {
                        try {
                            Boolean r = ecs.take().get();
                            if (r != null && r.booleanValue()) {
                                return true;
                            }

                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        } catch (ExecutionException ignore) {
                        }
                    }
                } finally {
                    for (Future<Boolean> f : futures) f.cancel(true);
                    pool.shutdown();
                }
            }

            state = transitions.get(c);
            if (state == null) return false;
        }
        if (acceptStates.contains(state)) return true;
        return false;
    }

    private void buildTransitionTable(Integer state, String p, int index) {
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
        char[] chars = p.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i-1 > 0 && chars[i-1] == '*') {
                if (chars[i-2] == chars[i]) {
                    char tmp = chars[i];
                    chars[i] = chars[i-1];
                    chars[i-1] = tmp;
                }
            }
        }
        return new String(chars);
    }

}
