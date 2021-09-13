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

public class ReverseKGroup {
    public static void main(String[] args) {
        String original = "[1,2,3,4,5]";
        ListNode head = deserialize(original);
        System.out.printf("original: %s%n", original);
        ListNode kReversed = (new ReverseKGroup()).reverseKGroup(head, 2);
        System.out.printf("kReversed: %s%n", serialize(kReversed));
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) return head;
        Deque<ListNode> stack = new ArrayDeque<>();
        ListNode n = head;
        ListNode newHead = new ListNode();
        ListNode newNode = newHead;
        stack.push(new ListNode(n.val));
        int i = 1;
        while ((n = n.next) != null) {
            stack.push(new ListNode(n.val));
            if (++i % k == 0) {
                while (stack.peek() != null) {
                    newNode = newNode.next = stack.pop();
                }
            }
        }
        while ((n = stack.pollLast()) != null) {
            newNode = newNode.next = n;
        }
        return newHead.next;
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
