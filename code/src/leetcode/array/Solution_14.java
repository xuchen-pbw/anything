package leetcode.array;
/*Best Time to Buy and Sell Stock
        Say you have an array for which the i^th element is the price of a given stock on day i.
        If you were only permitted to complete at most one transaction
        Note that you cannot sell a stock before you buy one.
        Example 1:
        Input: [7,1,5,3,6,4]
        Output: 5
        Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.

        Input: [7,6,4,3,1]
        Output: 0
        Explanation: In this case, no transaction is done, i.e. max profit = 0.*/

public class Solution_14 {
    public int maxProfit(int[] prices) {
        int maxCur = 0, maxSoFar = 0;
        for(int i = 1; i < prices.length; i++) {
            maxCur = Math.max(0, maxCur += prices[i] - prices[i-1]);
            maxSoFar = Math.max(maxCur, maxSoFar);
        }
        return maxSoFar;
    }
}
