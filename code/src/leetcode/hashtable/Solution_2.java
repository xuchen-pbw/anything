package leetcode.hashtable;
/*Single Number   XOR异或
        Given a non-empty array of integers, every element appears twice except for one.
        Find that single one.
        Example 1:
        Input: [2,2,1]
        Output: 1

        0 ^ N = N
        N ^ N = 0
        N1 ^ N1 ^ N2 ^ N2 ^..............^ Nx ^ Nx ^ N
        = (N1^N1) ^ (N2^N2) ^..............^ (Nx^Nx) ^ N
        = 0 ^ 0 ^ ..........^ 0 ^ N
        = N*/
public class Solution_2 {
    public int singleNumber(int[] nums) {
        int ans = 0;
        for(int i=0;i<nums.length;++i){
            ans ^= nums[i];
        }
        return ans;
    }
}
