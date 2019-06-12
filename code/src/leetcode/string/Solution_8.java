package leetcode.string;

public class Solution_8 {
    public String longestPalindrome(String s) {
        String max = "";
        for(int i=0;i<s.length();++i){
            String str1 = maxSubPalindromic(s,i,i);
            String str2 = maxSubPalindromic(s,i,i+1);
            if(str1.length()>max.length()) max = str1;
            if(str2.length()>max.length()) max = str2;
        }
        return max;
    }

    public String maxSubPalindromic(String str,int i,int j){
        for(;i>=0 && j<str.length();--i,++j){
            if(str.charAt(i) != str.charAt(j)) break;
        }
        return str.substring(i+1,j);
    }
}
