import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * This is a variant of leetcode 146.
 * implements LRU cache.
 * time complexity must be O(1).
 * must be thread-safe.
 *
Original LeetCode 146 description:

Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

146. LRU Cache
 Medium

Implement the LRUCache class:

LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
int get(int key) Return the value of the key if the key exists, otherwise return -1.
void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
The functions get and put must each run in O(1) average time complexity.


Example 1:
Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4
 

Constraints:

1 <= capacity <= 3000
0 <= key <= 104
0 <= value <= 105
At most 2 * 105 calls will be made to get and put.
 *
 * Discussion: how to improve the eviction efficiency to make the code production ready?
 */
public class LRU {

    public static void main(String[] args) {
        LRU lru = new LRU(2);
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        lru.put(3, 4);
        lru.put(1, -1);
        System.out.printf("one: %d%n", lru.get(1));
        System.out.printf("two: %d%n", lru.get(2));
        System.out.printf("three: %d%n", lru.get(3));
    }

    private int capacity;

    public LRU(int capacity) {
        this.capacity = capacity;
    }

    private Map<Integer, Node> cache = Collections.synchronizedMap(
            new WeakHashMap<>(capacity));

    private Node head = new Node();
    private Node tail = head;

    public Integer get(Integer key) {
        Node node = cache.get(key);
        if (node != null)  {
            moveOldNodeToFront(node);
            return node.val;
        }
        return null;
    }

    /**
     * returns repalced old Value or null if the key is new
     */
    public Integer put(Integer key, Integer val) {
        Integer replacedVal = null;
        Node existing = cache.get(key);
        if (existing == null) { // new key
            if (cache.size() >= capacity) evict();
            Node node = new Node(key, val);
            cache.put(key, node);
            addNewNodeToFront(node);
        } else { // replace exiting key
            replacedVal = existing.val;
            existing.key = key;
            existing.val = val;
            moveOldNodeToFront(existing);
        }
        return replacedVal;
    }

    private synchronized void addNewNodeToFront(Node key) {
        key.next = head;
        head.prev = key;
        head = key;
    }

    private synchronized void moveOldNodeToFront(Node touchedNode) {
        if (touchedNode == head) {
            head.key = touchedNode.key;
            head.val = touchedNode.val;
            return;
        }
        Node oldPrev = touchedNode.prev;
        Node oldNext = touchedNode.next;
        // System.out.printf("oldPrev=%s; oldNext=%s%n", oldPrev, oldNext);
        oldPrev.next = oldNext;
        oldNext.prev = oldPrev;
        touchedNode.next = head;
        touchedNode.prev = null;
        head.prev = touchedNode;
        head = touchedNode;
    }

    private void evict() {
        cache.remove(tail.prev.key);
        // System.out.printf("evicted %d%n", tail.prev.key);
        Node evicted = tail;
        tail = tail.prev;
        tail.next = null;
        evicted.prev = null;
        evicted = null;
    }

    class Node {
        Integer key;
        Integer val;
        Node prev;
        Node next;

        public Node() {
        }

        public Node(Integer key, Integer val) {
            this.key = key;
            this.val = val;
        }
    }
}

