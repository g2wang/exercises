/**
 *
 23. Merge k Sorted Lists

You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.

Merge all the linked-lists into one sorted linked-list and return it.

Example 1:

Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted list:
1->1->2->3->4->4->5->6
Example 2:

Input: lists = []
Output: []
Example 3:

Input: lists = [[]]
Output: []
 

Constraints:

k == lists.length
0 <= k <= 10^4
0 <= lists[i].length <= 500
-10^4 <= lists[i][j] <= 10^4
lists[i] is sorted in ascending order.
The sum of lists[i].length won't exceed 10^4.
 */
public class MergeKLists2 {

    public static void main(String[] args) {

        //ListNode[] lists = deserializeList("[[1,4,5],[1,3,4],[2,6]]");
        ListNode[] lists = deserializeList("[[],[-1,5,11],[],[6,10]]");

        System.out.printf("lists: %s%n", serialize(lists));

        ListNode result = (new MergeKLists2()).mergeKLists(lists);

        System.out.printf("result: %s%n", serialize(result));

    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        if (lists.length == 1) return lists[0];
        return merge(lists, 0, lists.length-1);
    }


    private ListNode merge(ListNode[] lists, int start,  int end) {
        if (start == end) {
            return lists[start];
        } else if (end - start == 1) {
            return merge2(lists[start], lists[end]);
        }
        int mid = (start + end)/2;
        ListNode l = merge(lists, start, mid);
        ListNode r = merge(lists, mid+1, end);
        return merge2(l, r);
    }

    private ListNode merge2(ListNode n1, ListNode n2) {
        ListNode result = new ListNode();
        ListNode n = result;
        while (n1 != null && n2 != null) {
            if (n1.val < n2.val) {
                n = n.next = n1;
                n1 = n1.next;
            } else {
                n = n.next = n2;
                n2 = n2.next;
            }
        }
        if (n1 != null) {
            n.next = n1;
        }
        if (n2 != null) {
            n.next = n2;
        }
        return result.next;
    }

    /**
     * listOfNodeSpec: [[1,4,5],[1,3,4],[2,6]]
     */
    private static ListNode[] deserializeList(String listOfNodeSpec) {
        if (listOfNodeSpec == null) return null;

        listOfNodeSpec = listOfNodeSpec.trim(); 
        while (listOfNodeSpec.startsWith("[")) {
            listOfNodeSpec = listOfNodeSpec.substring(1);
            listOfNodeSpec = listOfNodeSpec.trim(); 
        }
        while (listOfNodeSpec.endsWith("]")) {
            listOfNodeSpec = listOfNodeSpec.substring(0, listOfNodeSpec.length()-1);
            listOfNodeSpec = listOfNodeSpec.trim(); 
        }

        String[] tokens = listOfNodeSpec.split("\\],\\s*\\[");        

        ListNode[] list = new ListNode[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            list[i] = deserialize(tokens[i]);
        }

        return list;  
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

    private static String serialize(ListNode[] lists) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lists.length; i++) {
             if (i != 0) {
                sb.append(",");
            }
            sb.append(serialize(lists[i]));
        }
        sb.append("]");
        return sb.toString();
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
