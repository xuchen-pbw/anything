package leetcode;
/*Excel Sheet Column Number   二十六进制(暴力法 HashMap记录26个字母)
        Given a column title as appear in an Excel sheet, return its corresponding
        column number.
        Example 1:
        Input: "A"
        Output: 1
        Input: "AB"
        Output: 28
        Input: "ZY"
        Output: 701*/
public class Solution_4 {
    public int titleToNumber(String s) {
        int result = 0;
        for (int i = 0; i < s.length();result = result * 26 + (s.charAt(i) - 'A' + 1),i++);
        return result;
    }

    public int titleToNumber_1(String s) {
        int result = 0;
        for (int i = 0; i < s.length();i++)
            result = result * 26 + (s.charAt(i) - 'A' + 1);
        return result;
    }
}
