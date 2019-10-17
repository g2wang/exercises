public class ReverseLinkedList {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ReverseLinkedList r = new ReverseLinkedList();
        head = r.reverseList(head);
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

    public ListNode reverseList(ListNode head) {
        ListNode p = null; //previous
        ListNode c = head; //current
        while (c != null) {
            ListNode n = c.next; //next
            c.next = p;
            p = c;
            c = n;
        }
        return p;
    }
}
