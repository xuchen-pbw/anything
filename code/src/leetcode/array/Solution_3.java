package leetcode.array;
/*Remove Duplicates from Sorted Array
        Given a sorted array nums, remove the duplicates in-place such that each element appear only once
        and return the new length.Do not allocate extra space for another array, you must do this by
        modifying the input array in-place with O(1) extra memory.
        Given nums = [1,1,2],
        Your function should return length = 2, with the first two elements of nums being 1 and 2
        respectively.*/
public class Solution_3 {
    public int removeDuplicates(int[] nums) {
        if (nums.length==0) return 0;
        int j=0;
        for (int i=0; i<nums.length; i++)
            if (nums[i]!=nums[j]) nums[++j]=nums[i];
        return ++j;
    }
}
