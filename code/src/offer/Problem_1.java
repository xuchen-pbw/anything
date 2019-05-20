package offer;

public class Problem_1 {
}

class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}

 class Solution {
    public RandomListNode Clone(RandomListNode pHead)
    {
         if(pHead == null){ return null;}

        RandomListNode currentNode = pHead;
            while (currentNode != null){
                RandomListNode cloneNode = new RandomListNode(currentNode.label);
                RandomListNode nextNode = currentNode.next;
                currentNode.next = cloneNode;
                cloneNode.next = nextNode;
                currentNode = nextNode;
            }

            currentNode = pHead;
            while (currentNode != null){
                currentNode.next.random = currentNode.random==null?null:currentNode.random.next;
                currentNode = currentNode.next.next;
            }

            currentNode = pHead;
            RandomListNode cloneHead = pHead.next;
            while (currentNode != null){
                RandomListNode cloneNode = currentNode.next;
                currentNode.next = cloneNode.next;
                cloneNode.next = cloneNode.next==null?null:cloneNode.next.next;
                currentNode = cloneNode.next;
            }

            return cloneHead;
    }
}