public class UnionFind {
    int count; // the number of distinct sets
    int[] ranks; // ranks of a tree that represents a distinct set
    int[] parents; // parents of a element

    public UnionFind(int N) {
        parents = new int[N];
        ranks = new int[N];
        this.count = N;
        for (int i = 0; i < N; i++) {
            parents[i] = i;
            ranks[i] = 0;
        }
    }

    public int find(int x) {
        int p = x;
        while (parents[p] != p) {
            parents[p] = find(parents[p]);
            p = parents[p];
        }
        return p;
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return false;
        if (ranks[x] > ranks[y]) {
            parents[rootY] = rootX;
        } else if (ranks[x] < ranks[y]) {
            parents[rootX] = rootY;
        } else {
            parents[rootX] = rootY;
            ranks[y]++;
        }
        count--; 
        return true;
    }

    public boolean connected(int x, int y) {
        return (find(x) == find(y));
    }

    public int getNumDisjointSets() {
        return count;
    }

}
