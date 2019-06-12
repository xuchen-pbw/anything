package leetcode.hashtable;
/*Valid Anagram 相同字母异序词
        Given two strings s and t , write a function to determine if t is an anagram of s.
        Example 1:
        Input: s = "anagram", t = "nagaram"
        Output: true*/

public class Solution_1 {
    public boolean isAnagram(String s, String t) {
        //只考虑了小写26个字母
        int[] alphabet = new int[26];
        for (int i = 0; i < s.length(); i++) alphabet[s.charAt(i) - 'a']++;
        for (int i = 0; i < t.length(); i++) alphabet[t.charAt(i) - 'a']--;
        for (int i : alphabet) if (i != 0) return false;
        return true;
    }
}
