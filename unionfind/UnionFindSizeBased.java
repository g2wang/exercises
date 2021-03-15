import java.io.*;
import java.util.*;

/**
 * from https://www.hackerrank.com/challenges/merging-communities/problem
 * People connect with each other in a social network. A connection between Person  and Person  is represented as . When two persons belonging to different communities connect, the net effect is the merger of both communities which  and  belongs to.

At the beginning, there are  people representing  communities. Suppose person  and  connected and later  and  connected, then ,, and  will belong to the same community.

There are two type of queries:

 communities containing person  and  merged (if they belong to different communities).

 print the size of the community to which person  belongs.

Input Format

The first line of input will contain integers  and , i.e. the number of people and the number of queries.
The next  lines will contain the queries.

Constraints :



Output Format

The output of the queries.

Sample Input

3 6
Q 1
M 1 2
Q 2
M 2 3
Q 3
Q 2
Sample Output

1
2
3
3
Explanation

Initial size of each of the community is .
 */

public class UnionFindSizeBased {

    private int N; // number of people
    int[] parents;
    int[] sizes;
    private int numSets; // number of communities/disjoint sets

     // constructor
    public UnionFindSizeBased(int N) {
        this.N = N;
        parents = new int[N+1]; // 1 based array
        sizes = new int[N+1]; // 1 based array
        for (int i = 1; i <= N; i++) {
            parents[i] = i;
            sizes[i] = 1;
        }
        numSets = N;
    }

    public boolean union(int p1, int p2) {
        int g1 = find(p1);
        int g2 = find(p2);
        if (g1 == g2) {
            return false;
        }
        if (sizes[g1] > sizes[g2]) {
            parents[g2] = g1;
            sizes[g1]+= sizes[g2];
        } else {
            parents[g1] = g2;
            sizes[g2]+= sizes[g1];
        }
        numSets--;
        return true;
    }

    public int find(int p) {
        while (parents[p] != p) {
            parents[p] = parents[parents[p]]; // path compression by halving
            p = parents[p];
        }
        return p;
    }

    public int querySize(int p) {
        int g = find(p);
        int s = sizes[g];
        System.out.println(s);
        return s;
    }

    public int numOfDisjointSets() {
        return numSets;
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named UnionFindSizeBased. */
        Scanner stdIn = new Scanner(System.in);
        String line = stdIn.nextLine();
        String[] nq = line.split("\\s");
        int N = Integer.parseInt(nq[0]);
        int Q = Integer.parseInt(nq[1]);
        UnionFindSizeBased unionFind = new UnionFindSizeBased(N);
        while(stdIn.hasNextLine()) {
            line = stdIn.nextLine();
            if (line.startsWith("M")) {
                String[] params = line.split("\\s");
                int p1 = Integer.parseInt(params[1]);
                int p2 = Integer.parseInt(params[2]);
                unionFind.union(p1, p2);
            } else if (line.startsWith("Q")) {
                String[] params = line.split("\\s");
                int p = Integer.parseInt(params[1]);
                unionFind.querySize(p);
            }
        }
    }
}
