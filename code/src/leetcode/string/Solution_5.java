package leetcode.string;
/*Reverse String
        Write a function that reverses a string. The input string is given as an array of
        characters char[].
        Example 1:
        Input: ["h","e","l","l","o"]
        Output: ["o","l","l","e","h"]
        Example 2:
        Input: ["H","a","n","n","a","h"]
        Output: ["h","a","n","n","a","H"]*/
public class Solution_5 {
    public void reverseString(char[] s) {
        if(s.length<=0) return;
        char temp;
        int i = 0;
        int j = s.length - 1;
        while (i < j){
         temp = s[i];
         s[i] = s[j];
         s[j] = temp;
            ++i;
            --j;
        }
    }
}
