package leetcode.string;
/*Valid Parentheses
        Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
        determine if the input string is valid.
        An input string is valid if:
        Open brackets must be closed by the same type of brackets.
        Open brackets must be closed in the correct order.
        Note that an empty string is also considered valid.
        Example 1:
        Input: "()"
        Output: true

        Input: "()[]{}"
        Output: true

        Input: "([)]"
        Output: false

        Input: "{[]}"
        Output: true

        Input: "(]"
        Output: false*/
import java.util.Stack;

public class Solution_2 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();

    }
}
