package leetcode.linkedlist;
/*判断一个链表是否包含环*/
public class Solution_3 {
    public boolean hasCycle(ListNode head){
        ListNode walker = head;
        ListNode runner = head;
        while(runner!=null && runner.next!=null) {
            walker = walker.next;
            runner = runner.next.next;
            if(walker==runner) return true;
        }
        return false;
    }

    public boolean hasCycle_1(ListNode head) {
        if(head==null) return false;
        ListNode walker = head;
        ListNode runner = head;
        while(runner.next!=null && runner.next.next!=null) {
            walker = walker.next;
            runner = runner.next.next;
            if(walker==runner) return true;
        }
        return false;
    }
}
