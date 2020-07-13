/**
752. Open the Lock
Medium

You have a lock in front of you with 4 circular wheels. Each wheel has 10
slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate
freely and wrap around: for example we can turn '9' to be '0', or '0' to be
'9'. Each move consists of turning one wheel one slot.

The lock initially starts at '0000', a string representing the state of the 4
wheels.

You are given a list of deadends dead ends, meaning if the lock displays any of
these codes, the wheels of the lock will stop turning and you will be unable to
open it.

Given a target representing the value of the wheels that will unlock the lock,
return the minimum total number of turns required to open the lock, or -1 if it
is impossible.

Example 1:
Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
Output: 6
Explanation:
A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
because the wheels of the lock become stuck after the display becomes the dead end "0102".

Example 2:
Input: deadends = ["8888"],
target = "0009"
Output: 1
Explanation:
We can turn the last wheel in reverse to move from "0000" -> "0009".

Example 3:
Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"],
target = "8888"
Output: -1
Explanation:
We can't reach the target without getting stuck.

Example 4:
Input: deadends = ["0000"],
target = "8888"
Output: -1

Note:
The length of deadends will be in the range [1, 500].
target will not be in the list deadends.
Every string in deadends and the string target will be a string of 4 digits
from the 10,000 possibilities '0000' to '9999'. 
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;

public class OpenLock2 {

    public static void main(String[] args) {
        String[] deadends = new String[]{"0201","0101","0102","1212","2002"};
        String target = "0202";
        OpenLock2 ol = new OpenLock2();
        int ans = ol.openLock(deadends, target);
        System.out.printf("ans = %d%n", ans);
        assert ans == 6;
    }


    /**
     * copyright: author of a submission to LeetCode
     */
    public int openLock(String[] deadends, String target) {
        Lock initial = new Lock(target);
        Set<Lock> deads = new HashSet<>();
        for(String s: deadends) {
            deads.add(new Lock(s));
        }
        if(deads.contains(new Lock("0000")) || deads.contains(initial)) {
            return -1;
        }
        Queue<Lock> toProcess = new PriorityQueue<>();
        toProcess.add(initial);
        return openLock(toProcess, deads);
    }
    
    private int openLock(Queue<Lock> toProcess, Set<Lock> deadends) {
        Lock lock = null;
        while((lock = toProcess.poll()) != null) {
            if(deadends.contains(lock)) {
                continue;
            }
            if(lock.isUnlocked()) {
                return lock.turns;
            }
            for(Lock nextOne: lock.makeTurn()) {
                toProcess.add(nextOne);
            }
            deadends.add(lock);
        }
        return -1;
    }
    
    // Inter class
    private static class Lock implements Comparable<Lock> {
        int w1, w2, w3, w4;
        int turns;

        Lock(String initial) {
            char[] c = initial.toCharArray();
            w1 = c[0] - '0';
            w2 = c[1] - '0';
            w3 = c[2] - '0';
            w4 = c[3] - '0';
        }

        Lock(int w1, int w2, int w3, int w4, int turns) {
            this.w1 = (w1 + 10) % 10;
            this.w2 = (w2 + 10) % 10;
            this.w3 = (w3 + 10) % 10;
            this.w4 = (w4 + 10) % 10;
            this.turns = turns;
        }

        boolean isUnlocked() {
            return (w1 == 0) && (w2 == 0) && (w3 == 0) && (w4 == 0);
        }

        Set<Lock> makeTurn() {
            Set<Lock> nextOnes = new HashSet<Lock>();
            nextOnes.add(new Lock(w1 + 1, w2, w3, w4, turns+1));
            nextOnes.add(new Lock(w1 - 1, w2, w3, w4, turns+1));
            nextOnes.add(new Lock(w1, w2 + 1, w3, w4, turns+1));
            nextOnes.add(new Lock(w1, w2 - 1, w3, w4, turns+1));
            nextOnes.add(new Lock(w1, w2, w3 + 1, w4, turns+1));
            nextOnes.add(new Lock(w1, w2, w3 - 1, w4, turns+1));
            nextOnes.add(new Lock(w1, w2, w3, w4 + 1, turns+1));
            nextOnes.add(new Lock(w1, w2, w3, w4 - 1, turns+1));

            return nextOnes;
        }

        public int compareTo(Lock other) {
            int wx1 = Math.abs(w1 - 5) + Math.abs(w2 - 5) + Math.abs(w3 - 5) + Math.abs(w4 - 5);
            int wx2 = Math.abs(other.w1 - 5) + Math.abs(other.w2 - 5) + Math.abs(other.w3 - 5) + Math.abs(other.w4 - 5);
            return wx2 - wx1;
        }

        public int hashCode() {
            return w1 * 10 * 10 * 10 + w2 * 10 * 10 + w3 * 10 + w4;
        }

        public boolean equals(Object other) {
            if(this == other) {
                return true;
            }
            Lock obj = (Lock)other;
            return (w1 == obj.w1) && (w2 == obj.w2) && (w3 == obj.w3) && (w4 == obj.w4);
        }
    }

}
