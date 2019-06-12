package leetcode.string;
/*Longest Common Prefix
        Write a function to find the longest common prefix string amongst an array of strings.
        If there is no common prefix, return an empty string "".
        Example 1:
        Input: ["flower","flow","flight"]
        Output: "fl"*/
public class Solution_6 {
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0)    return "";
        String pre = strs[0];
        int i = 1;
        while(i < strs.length){
            while(strs[i].indexOf(pre) != 0)
                pre = pre.substring(0,pre.length()-1);
            i++;
        }
        return pre;
    }
}
