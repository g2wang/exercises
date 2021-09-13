/**
25. Reverse Nodes in k-Group

Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.

You may not alter the values in the list's nodes, only nodes themselves may be changed.

Example 1:

￼
Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]
Example 2:

￼
Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]
Example 3:

Input: head = [1,2,3,4,5], k = 1
Output: [1,2,3,4,5]
Example 4:

Input: head = [1], k = 1
Output: [1]


Constraints:

The number of nodes in the list is in the range sz.
1 <= sz <= 5000
0 <= Node.val <= 1000
1 <= k <= sz


Follow-up: Can you solve the problem in O(1) extra memory space?
 */
import java.util.ArrayDeque;
import java.util.Deque;

public class ReverseKGroup2 {
    public static void main(String[] args) {
        String original = "[1,2,3,4,5]";
        ListNode head = deserialize(original);
        System.out.printf("original: %s%n", original);
        ListNode kReversed = (new ReverseKGroup2()).reverseKGroup(head, 2);
        System.out.printf("kReversed: %s%n", serialize(kReversed));
    }
    
    /**
     * copyright: author of LeetCode submission
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode end = dummy;
        while(end.next != null){
            for(int i = 0; i < k && end != null; i++){
                end = end.next;
            }
            if(end == null) break;
            ListNode start = pre.next;
            ListNode next = end.next;
            end.next = null;
            pre.next = reverse(start);
            start.next = next;
            pre = start;

            end = pre;
        }
        return dummy.next;
    }

    private ListNode reverse(ListNode head){
        ListNode pre = null;
        ListNode curr = head;
        while(curr != null){
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    /**
     * nodeSpec - [1,4,5]
     */
    private static ListNode deserialize(String nodeSpec) {
        if (nodeSpec == null) return null;
        nodeSpec = nodeSpec.trim(); 
        if (nodeSpec.startsWith("[")) {
            nodeSpec = nodeSpec.substring(1);
        }
        if (nodeSpec.endsWith("]")) {
            nodeSpec = nodeSpec.substring(0, nodeSpec.length()-1);
        }
        if (nodeSpec == null || nodeSpec.trim().isEmpty()) return null;

        String[] tokens = nodeSpec.split(",\\s*");
        ListNode result = new ListNode(Integer.parseInt(tokens[0]));
        ListNode node = result;
        for (int i = 1; i < tokens.length; i++) {
            node = node.next = new ListNode(Integer.parseInt(tokens[i]));
        }
        return result;  
    }

    private static String serialize(ListNode node) {
        StringBuilder sb = new StringBuilder("[");
        if (node != null) {
            boolean first = true;
            do {
                if (first) {
                    first = false;
                } else {
                    sb.append(",");
                }
                sb.append(node.val);
                node = node.next;
            } while(node != null);
        }
        sb.append("]");
        return sb.toString(); 
    }

}
