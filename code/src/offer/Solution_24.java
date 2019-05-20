package offer;
/*链表中倒数第k个结点
        输入一个链表，输出该链表中倒数第k个结点。*/

import java.util.ArrayList;
/*class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution_24 {
    public ListNode FindKthToTail(ListNode head,int k) {
        ArrayList<ListNode> arrayList = new ArrayList<ListNode>();
        while(head != null){
            arrayList.add(head);
            head = head.next;
        }
        int length = arrayList.size();
        if(k>length || k<=0)
            return null;
        return arrayList.get(length-k);

    }
}
