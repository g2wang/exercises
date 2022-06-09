/**
146. LRU Cache
Medium

Design and implement a data structure for Least Recently Used (LRU) cache. It
should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists
in the cache, otherwise return -1.

put(key, value) - Set or insert the value if the key is not already present.
When the cache reached its capacity, it should invalidate the least recently
used item before inserting a new item.

The cache is initialized with a positive capacity.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LRUCache cache = new LRUCache(2); // 2 is the capacity

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.put(4, 4);    // evicts key 1
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4

 */

import java.util.Map;
import java.util.HashMap;

public class LRUCache {

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2); // 2 is the capacity

        cache.put(1, 1);
        cache.put(2, 2);
        int c1 = cache.get(1); // returns 1
        if (c1 != 1) {
            System.err.printf("error: cache.get(1) returned wrong answer %d; should be 1\n", c1);
        }
        cache.put(3, 3); // evicts key 2
        int c2 = cache.get(2);  // returns -1 (not found)
        if (c2 != -1) {
            System.err.printf("error: cache.get(2) returned wrong answer %d; should be -1\n", c2);
        }
        cache.put(4, 4);    // evicts key 1
        c1 = cache.get(1);       // returns -1 (not found)
        if (c1 != -1) {
            System.err.printf("error: cache.get(1) returned wrong answer %d; should be -1\n", c1);
        }
        int c3 = cache.get(3);       // returns 3
        if (c3 != 3) {
            System.err.printf("error: cache.get(3) returned wrong answer %d; should be 3\n", c3);
        }
        int c4 = cache.get(4);       // returns 4
        if (c4 != 4) {
            System.err.printf("error: cache.get(4) returned wrong answer %d; should be 4\n", c4);
        }

    }

    Node head;
    Node tail;
    int capacity;
    Map<Integer, Node> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node n = map.get(key);
        if (n == null) return -1;
        removeNode(n);
        addToHead(n);
        return n.val;
    }

    public void put(int key, int value) {
        Node n0 = map.get(key);
        if (n0 == null && map.size() == capacity) {
            Node t = removeTail();
            map.remove(t.key);
        }
        Node n = new Node(key, value);
        if (n0 != null) removeNode(n0);
        addToHead(n);
        map.put(key, n);
    }

    private void addToHead(Node n) {
        Node hn = head.next;
        n.next = hn;
        hn.prev = n;
        head.next = n;
        n.prev = head;
    }

    private void removeNode(Node n) {
        Node p = n.prev;
        Node x = n.next;
        p.next = x;
        x.prev = p;
    }

    private Node removeTail() {
        Node t = tail.prev;
        Node pt = t.prev;
        pt.next = tail;
        tail.prev = pt;
        return t;
    }

    private class Node {
        int key, val;
        Node prev, next;
        public Node(int key, int val) {
            this.key = key;  this.val= val;
        }
    }

}
