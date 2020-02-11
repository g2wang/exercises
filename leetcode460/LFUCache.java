/**
 *
460. LFU Cache
Hard

get(key) - Get the value (will always be positive) of the key if the key exists
in the cache, otherwise return -1.

put(key, value) - Set or insert the value if the key is not already present.
When the cache reaches its capacity, it should invalidate the least frequently
used item before inserting a new item. For the purpose of this problem, when
there is a tie (i.e., two or more keys that have the same frequency), the least
recently used key would be evicted.

Note that the number of times an item is used is the number of calls to the get
and put functions for that item since it was inserted. This number is set to
zero when the item is removed.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LFUCache cache = new LFUCache(2); // 2 is capacity 

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.get(3);       // returns 3.
cache.put(4, 4);    // evicts key 1.
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4

==== this solution ============
Runtime: 20 ms, faster than 92.09% of Java online submissions for LFU Cache.
Memory Usage: 53.9 MB, less than 85.00% of Java online submissions for LFU Cache.
 */

import java.util.Map;
import java.util.HashMap;

public class LFUCache {

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2); // 2 is the capacity

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
        int c3 = cache.get(3); // returns 3.
        if (c3 != 3) {
            System.err.printf("error: cache.get(3) returned wrong answer %d; should be 3\n", c2);
        }
        cache.put(4, 4); // evicts key 1
        c1 = cache.get(1); // returns -1 (not found)
        if (c1 != -1) {
            System.err.printf("error: cache.get(1) returned wrong answer %d; should be -1\n", c1);
        }
        c3 = cache.get(3); // returns 3
        if (c3 != 3) {
            System.err.printf("error: cache.get(3) returned wrong answer %d; should be 3\n", c3);
        }
        int c4 = cache.get(4); // returns 4
        if (c4 != 4) {
            System.err.printf("error: cache.get(4) returned wrong answer %d; should be 4\n", c4);
        }

    }

    // key -> frequency map
    HashMap<Integer, Integer> kfMap = new HashMap<>();

    // frequency -> (key -> value map) map
    HashMap<Integer, AccessOrderedHashMap<Integer, Integer>>
        fkvMap = new HashMap<>();

    int capacity;
    int fmin = 0; // minimum frequency
    int size = 0;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    private AccessOrderedHashMap<Integer, Integer> mkMap() {
        return new AccessOrderedHashMap<>();
    }

    private void increaseFrequency(
        AccessOrderedHashMap<Integer, Integer> kvMap,
        int key, int f, int value) {
        // increase frequency count for this key
        AccessOrderedHashMap<Integer, Integer>.Node<Integer, Integer>
           n = kvMap.remove(key);
        kfMap.remove(n.key);
        if (kvMap.isEmpty()) {
            fkvMap.remove(f);
            if (f == fmin) fmin++;
        }

        f++;
        kfMap.put(key, f);
        kvMap = fkvMap.get(f);
        if (kvMap == null) {
            kvMap = mkMap();
            fkvMap.put(f, kvMap);
        }
        kvMap.put(key, value);
    }

    public int get(int key) {
        Integer f = kfMap.get(key);
        if (f == null) return -1;
        AccessOrderedHashMap<Integer, Integer> kvMap = fkvMap.get(f);
        if (kvMap == null) return -1;
        Integer value = kvMap.get(key);
        if (value == null) return -1;

        // increase frequency count for this key
        increaseFrequency(kvMap, key, f, value);

        return value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        AccessOrderedHashMap<Integer, Integer> kvMap = null;
        Integer f = kfMap.get(key);
        if (f != null) kvMap = fkvMap.get(f);
        if (kvMap == null) { // new key
            size++;
            if (size > capacity) {
                // remove the least frequently and least recently used
                kvMap = fkvMap.get(fmin);
                AccessOrderedHashMap<Integer, Integer>
                    .Node<Integer, Integer> tail
                    = kvMap.removeTail();
                kfMap.remove(tail.key);
                if (kvMap.isEmpty()) {
                    fkvMap.remove(fmin);
                }
                size--;
            }
            kvMap = fkvMap.get(1);
            if (kvMap == null) {
                kvMap = mkMap();
                fkvMap.put(1, kvMap);
            }
            kfMap.put(key, 1);
            kvMap.put(key, value);
            fmin = 1;
        } else { // existing key
            // increase frequency count for this key
            increaseFrequency(kvMap, key, f, value);
        }
    }

    private class AccessOrderedHashMap<K,V> {
        class Node<K,V> {
            K key; V val;
            Node<K,V> prev, next;
            public Node(K key, V val) {
                this.key = key; this.val = val;
            }
        }

        Node<K,V> head, tail;
        HashMap<K,Node<K,V>> map = new HashMap<>();

        public AccessOrderedHashMap() {
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
        }

        public void put(K key, V val) {
            Node<K,V> n = map.get(key);
            if (n == null) { // new key
                n = new Node<>(key, val);
                Node<K,V> hn = head.next;
                head.next = n;
                n.prev = head;
                n.next = hn;
                hn.prev = n;
            } else { // exisiting key
                n.val = val;
                if (head.next != n) {
                    Node<K,V> np = n.prev;
                    Node<K,V> nn = n.next;
                    np.next = nn;
                    nn.prev = np;
                    Node<K,V> hn = head.next;
                    head.next = n;
                    n.prev = head;
                    n.next = hn;
                    hn.prev = n;
                }
            }
            map.put(key, n);
        }

        public V get(K key) {
            Node<K,V> n = map.get(key);
            if (n == null) return null;
            if (head.next != n) {
                Node<K,V> np = n.prev;
                Node<K,V> nn = n.next;
                np.next = nn;
                nn.prev = np;
                Node<K,V> hn = head.next;
                head.next = n;
                n.prev = head;
                n.next = hn;
                hn.prev = n;
            }
            return n.val;
        }

        public Node<K,V> remove(K key) {
            Node<K,V> n = map.get(key);
            if (n == null) return null;
            map.remove(key);
            Node<K,V> np = n.prev;
            Node<K,V> nn = n.next;
            np.next = nn;
            nn.prev = np;
            return n;
        }

        public Node<K,V> removeTail() {
            Node<K,V> n = tail.prev;
            if (head == n) return null;
            Node<K,V> np = n.prev;
            np.next = tail;
            tail.prev = np;
            map.remove(n.key);
            return n;
        }

        public int size() {
            return map.size();
        }

        public boolean isEmpty() {
            return map.isEmpty();
        }
    }
}
