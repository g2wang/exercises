/**
 * UnionFind with union by rank and path compression heuristics
 */
public class UnionFind {
    int count; // the number of distinct sets
    int[] rank; // rank of a tree that represents a distinct set
    int[] parent; // id of a element

    public UnionFind(int N) {
        parent = new int[N+1];
        rank = new int[N+1];
        this.count = N;
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        while (x != parent[x]) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return false;
        link(rootX, rootY);
        count--;
        return true;
    }

    private void link(int x, int y) {
        if (rank[x] > rank[y]) {
            parent[y] = x;
        } else {
            parent[x] = y;
            if (rank[x] == rank[y]) rank[y]++;
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public int getNumDisjointSets() {
        return count;
    }

}
