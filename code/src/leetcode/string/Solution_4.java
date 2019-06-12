package leetcode.string;
/*Roman to Integer
        Example 1:
        Input: "III"
        Output: 3
        Input: "IV"
        Output: 4
        Input: "MCMXCIV"
        Output: 1994
        Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.*/
import java.util.HashMap;

public class Solution_4 {
    public int romanToInt(String s) {
        char[] c = s.toCharArray();
        int sum = 0;
        if(c.length<=0) return 0;
        HashMap<Character,Integer> map = new HashMap<>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);
        for(int i=0;i<c.length;i++){
            if(map.containsKey(c[i]))
                sum += map.get(c[i]);
        }
        for(int i=1;i<c.length;i++){
            if(c[i]=='V' && c[i-1]=='I')
                sum = sum - 2;
            if(c[i]=='X' && c[i-1]=='I')
                sum = sum - 2;
            if(c[i]=='L' && c[i-1]=='X')
                sum = sum - 20;
            if(c[i]=='C' && c[i-1]=='X')
                sum = sum - 20;
            if(c[i]=='D' && c[i-1]=='C')
                sum = sum - 200;
            if(c[i]=='M' && c[i-1]=='C')
                sum = sum - 200;
        }
        return sum;
    }
}
