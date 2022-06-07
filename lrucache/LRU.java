import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

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

