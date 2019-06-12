package leetcode.array;
/*Contains Duplicate
        Given an array of integers, find if the array contains any duplicates.
        Example 1:
        Input: [1,2,3,1]
        Output: true*/
import java.util.HashMap;

public class Solution_12 {
    public boolean containsDuplicate(int[] nums) {
        if(nums==null || nums.length<=1){
            return false;
        }
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i]))
                return true;
            else
                map.put(nums[i],i);
        }
        return false;
    }
}
