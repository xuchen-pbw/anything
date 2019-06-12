package leetcode.linkedlist;
/*反转链表
  链表的引用与实体
  递归在链表中的体现像拼凑*/
public class Solution_6 {
    public ListNode reverseList(ListNode head) {
        return putPreAfterNode(head, null);
    }

    private ListNode putPreAfterNode(ListNode node, ListNode pre) {
        if (node == null) {
            return pre;
        }
        ListNode next = node.next;
        node.next = pre;
        return putPreAfterNode(next, node);
    }
}
