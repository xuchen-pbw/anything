package leetcode.string;
/*Valid Palindrome 回文
        Given a string, determine if it is a palindrome, considering only alphanumeric characters
        and ignoring cases
        Example 1:
        Input: "A man, a plan, a canal: Panama"
        Output: true

        Input: "race a car"
        Output: false*/
public class Solution_3 {
    public boolean isPalindrome(String s) {
        char[] c = s.toCharArray();
        for (int i = 0, j = c.length - 1; i < j; ) {
            if (!Character.isLetterOrDigit(c[i])) i++;
            else if (!Character.isLetterOrDigit(c[j])) j--;
            else if (Character.toLowerCase(c[i++]) != Character.toLowerCase(c[j--]))
                return false;
        }
        return true;
    }
}
