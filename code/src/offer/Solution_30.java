package offer;
/*删除链表中重复的结点
        在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，
        返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5*/
public class Solution_30 {
    public ListNode deleteDuplication(ListNode pHead)
    {
        //设置一个trick 仅用于返回头结点  并解决了第一个结点是重复结点的问题
        ListNode first = new ListNode(-1);

        first.next = pHead;

        ListNode p = pHead;
        ListNode last = first;
        while (p != null && p.next != null) {
            if (p.val == p.next.val) {
                int val = p.val;
                //重复结点可能有很多个
                while (p!= null&&p.val == val)
                    p = p.next;
                last.next = p;
            } else {
                last = p;
                p = p.next;
            }
        }
        return first.next;

    }
}
