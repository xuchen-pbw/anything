package leetcode.stack;
/*evaluate-reverse-polish-notation  逆波兰数
        Evaluate the value of an arithmetic expression in Reverse Polish Notation.
        Valid operators are+,-,*,/. Each operand may be an integer or another expression.
        Some examples:
        ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
        ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6*/
import java.util.Stack;
public class Solution_1 {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<Integer>();
        for(int i=0;i<tokens.length;i++){
            try{
                //字符数组转换为普通数字
                int num = Integer.parseInt(tokens[i]);
                stack.add(num);
            }catch(Exception e){
                //先出栈b  再出栈a  与后面get方法中计算顺序一致
                int b = stack.pop();
                int a = stack.pop();
                stack.add(get(a,b,tokens[i]));
            }
        }
        return stack.pop();
    }
    private int get(int a,int b, String operate){
        switch(operate){
            case "+":
                return a+b;
            case "-":
                return a-b;
            case "*":
                return a*b;
            case "/":
                return a/b;
            default:
                return 0;
        }
    }
}
