package leetcode.linkedlist;
/*寻找两个链表的相交结点
  Write a program to find the node at which the intersection of two singly linked
  lists begins.

  You can prove that: say A length = a + c, B length = b + c, after switching pointer,
  pointer A will move another b + c steps, pointer B will move a + c more steps,
  since a + c + b + c = b + c + a + c, it does not matter what value c is.
  Pointer A and B must meet after a + c + b or b + c + a steps. If c == 0,
  they meet at NULL*/
public class Solution_2 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB){
        if( headA==null || headB==null){
            return null;
        }
        ListNode a = headA;
        ListNode b = headB;
        //if a & b have different len, then we will stop the loop after second iteration
        //如果a，b有相同长度，并且有相交的结点，则第一次循环就能找出相交结点
        while (a!=b){
            //三元运算与赋值运算的顺序？
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }
}
