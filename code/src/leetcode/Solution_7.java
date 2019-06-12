package leetcode;
/*House Robber    数组中不能取相邻值的最大和
        You are a professional robber planning to rob houses along a street.
        Each house has a certain amount of money stashed, the only constraint
        stopping you from robbing each of them is that adjacent houses have security
        system connected and it will automatically contact the police if two adjacent
        houses were broken into on the same night.*/
public class Solution_7 {
    public int rob(int[] num) {
        int[][] dp = new int[num.length + 1][2];
        for (int i = 1; i <= num.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = num[i - 1] + dp[i - 1][0];
        }
        return Math.max(dp[num.length][0], dp[num.length][1]);
    }
}
