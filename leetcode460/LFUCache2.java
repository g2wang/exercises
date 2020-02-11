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

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class LFUCache2 {

    public static void main(String[] args) {
        LFUCache2 cache = new LFUCache2(2); // 2 is the capacity

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

    ///////// copyright: author of the submission to LeetCode /////////////
    
    ArrayList<Node> freHeads;
    ArrayList<Node> freTails;
    int capacity;
    int cnt;
    int lowestFre;
    Map<Integer, Node> map;
    public LFUCache2(int capacity) {
        this.freHeads = new ArrayList<>();
        this.freTails = new ArrayList<>();
        this.capacity = capacity;
        this.cnt = 0;
        this.lowestFre = 0;
        this.map = new HashMap<>();

        //build first row in frequency table
        Node head = new Node(0, 0);
        Node tail = new Node(0, 0);
        freHeads.add(head);
        freTails.add(tail);
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        if(map.containsKey(key)){
            Node res = map.get(key);
            moveUp(res); //move up this item by 1 in the frequency table
            return res.val;
        }else{
            return -1;
        }
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node res = map.get(key);
            res.val = value;
            moveUp(res);  //move up this item by 1 in the frequency table
        }else if(capacity != 0){//corner case. if capacity == 0, never do anything.
            if(cnt < capacity){
                cnt++;
            }else{//remove the lowestFrequency row's last item
                Node tail = freTails.get(lowestFre);
                map.remove(tail.pre.key);
                deleteNode(tail.pre); //delete from the tail of the lowest frequency row
            }
            Node res = new Node(value, key);
            map.put(key, res);
            addFirst(res, freHeads.get(0)); //add new item to the 1st row
            lowestFre = 0; //new item added, lowestFrequency becomes 0
        }
    }

    private void deleteNode(Node n){
        n.next.pre = n.pre;
        n.pre.next = n.next;
    }

    private void moveUp(Node node){
        if(node.pre.pre == null && node.next.next==null && node.fre == lowestFre) lowestFre++;
        //if this item is the only item and this is the lowestFrequency row, increase lowestFre by 1
        node.fre += 1;
        deleteNode(node);// delete this item from this row

       //build a new row if there isn't one
        if(node.fre >= freHeads.size()){
            Node head = new Node(0, 0);
            Node tail = new Node(0, 0);
            freHeads.add(head);
            freTails.add(tail);
            head.next = tail;
            tail.pre = head;
        }
        addFirst(node, freHeads.get(node.fre)); //add it to the head of the next row
    }

    private void addFirst(Node n, Node head){
        n.next = head.next;
        n.pre = head;
        head.next = n;
        n.next.pre = n;
    }

    class Node{
        Node pre;
        Node next;
        int val;
        int key;
        int fre;
        public Node(int v, int k){
            this.val = v;
            this.key = k;
            this.fre = 0;
        }
    }

}
