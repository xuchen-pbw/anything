package leetcode.array;

import java.util.Arrays;

/*Missing Number
        Given an array containing n distinct numbers taken from 0, 1, 2, ..., n,
        find the one that is missing from the array.
        Input: [9,6,4,2,3,5,7,0,1]
        Output: 8*/
public class Solution_8 {
    public int missingNumber(int[] nums) {
        Arrays.sort(nums);
        int left = 0, right = nums.length, mid= (left + right)/2;
        while(left<right){
            mid = (left + right)/2;
            if(nums[mid]>mid) right = mid;
            else left = mid+1;
        }
        return left;

    }

    public int missingNumber_1(int[] nums) {

        int xor = 0, i = 0;
        for (i = 0; i < nums.length; i++) {
            xor = xor ^ i ^ nums[i];
        }
        return xor ^ i;
    }
}
