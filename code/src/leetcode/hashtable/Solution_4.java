package leetcode.hashtable;
/*Happy Number
        Write an algorithm to determine if a number is "happy".
        A happy number is a number defined by the following process: Starting with any positive
        integer, replace the number by the sum of the squares of its digits, and repeat the
        process until the number equals 1 (where it will stay), or it loops endlessly in a
        cycle which does not include 1. Those numbers for which this process ends in 1 are happy
        numbers.
        Example:
        Input: 19
        Output: true
        Explanation:
        12 + 92 = 82
        82 + 22 = 68
        62 + 82 = 100
        12 + 02 + 02 = 1

        HashSet的add方法，返回值是一个boolean，即Set中无序不重复
        public boolean add(E e) {
        return map.put(e, PRESENT)==null;
        }*/

import java.util.HashSet;
import java.util.Set;

public class Solution_4 {
    public boolean isHappy(int n) {
        Set<Integer> inLoop = new HashSet<Integer>();
        int squareSum,remain;
        while (inLoop.add(n)) {
            squareSum = 0;
            while (n > 0) {
                remain = n%10;
                squareSum += remain*remain;
                n /= 10;
            }
            if (squareSum == 1)
                return true;
            else
                n = squareSum;

        }
        return false;
    }
}
