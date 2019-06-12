package leetcode.array;
/*Majority Element
        Given an array of size n, find the majority element. The majority element is the element that
        appears more than ⌊ n/2 ⌋ times.
        Example 2:
        Input: [2,2,1,1,1,2,2]
        Output: 2*/

import java.util.Arrays;

public class Solution_11 {
    public int majorityElement(int[] nums) {
        int length = nums.length;
        if(nums==null || length<=0){
            return 0;
        }
        Arrays.sort(nums);
        return nums[length/2];
    }
}
