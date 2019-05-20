package offer;
/*用两个栈实现队列
用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。*/
import java.util.Stack;

public class Solution_15 {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if(stack1.isEmpty() && stack2.isEmpty())
            throw new RuntimeException("Queue is Empty");
        int node;
        if(stack2.isEmpty()){
            while(!stack1.isEmpty()){
                node = stack1.pop();
                stack2.push(node);
            }
        }
        return stack2.pop();
    }
}
