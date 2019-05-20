package offer;
/*合并两个排序的链表
        输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。*/
public class Solution_26 {
    public ListNode Merge(ListNode list1,ListNode list2) {
        if(list1==null)
            return list2;
        else if(list2==null)
            return list1;
        ListNode mergehead = null;
        if(list1.val<=list2.val){
            mergehead = list1;
            mergehead.next = Merge(list1.next,list2);
        }else{
            mergehead = list2;
            mergehead.next = Merge(list1,list2.next);
        }
        return mergehead;
    }
}
