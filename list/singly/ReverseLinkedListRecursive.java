public class ReverseLinkedListRecursive {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ReverseLinkedListRecursive r = new ReverseLinkedListRecursive();
        head = r.reverseListRecursive(null, head);
        ListNode c = head;
        StringBuilder sb = new StringBuilder();
        while (c != null) {
            sb.append(c.val).append(", ");
            c = c.next;
        }
        System.out.println(sb.toString());
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode reverseListRecursive(ListNode p, ListNode c) {
        if (c == null) return p;
        ListNode n = c.next;
        c.next = p;
        return reverseListRecursive(c, n);
    }
}
