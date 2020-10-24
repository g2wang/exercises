/**
 * UnionFind with union by rank and path compression heuristics
 */
public class UnionFind {
    int count; // the number of distinct sets
    int[] rank; // rank of a tree that represents a distinct set
    int[] parent; // id of a element

    public UnionFind(int N) {
        parent = new int[N];
        rank = new int[N];
        this.count = N;
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        if (x != parent[x]) 
            parent[x] = find(parent[x]);
        return parent[x];
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
