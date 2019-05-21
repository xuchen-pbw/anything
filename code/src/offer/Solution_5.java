package offer;
/*包含min函数的栈
        定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））*/
import java.util.Stack;
public class Solution_5 {
    Stack<Integer> stack = new Stack<Integer>();
    Stack<Integer> temp = new Stack<Integer>();
    int min = Integer.MAX_VALUE;
    public void push(int node) {
        stack.push(node);
        if(node<min){
            temp.push(node);
            min = node;
        }else
            temp.push(min);
    }

    public void pop() {
        stack.pop();
        temp.pop();
    }

    public int top() {
        int t = stack.pop();
        stack.push(t);
        return t;
    }

    public int min() {
        int m = temp.pop();
        temp.push(m);
        return m;
    }

}
