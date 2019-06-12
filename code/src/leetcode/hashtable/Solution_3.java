package leetcode.hashtable;
/*Intersection of Two Arrays II  两个数组的十字路口，即是两个数组的重复的元素
        Given two arrays, write a function to compute their intersection.
        Example 1:
        Input: nums1 = [1,2,2,1], nums2 = [2,2]
        Output: [2,2]

        返回的是键值 map.getOrDefault(num, 0) HashMap中有num这个键，就用num这个键对应的值
                                              没有num这个键，返回默认值(这里是0)
        */
import java.util.ArrayList;
import java.util.HashMap;

public class Solution_3 {
    public int[] intersect(int[] nums1, int[] nums2) {
        // freq count for nums1
        HashMap<Integer, Integer> map = new HashMap();
        for (int num : nums1) map.put(num, map.getOrDefault(num, 0) + 1);
        // collect result
        ArrayList<Integer> result = new ArrayList();
        for (int num: nums2) {
            if (map.containsKey(num)){
                result.add(num);
                map.put(num, map.get(num) - 1);
                map.remove(num, 0);
            }
        }
        // convert result 将ArrayList转换为普通数组
        int[] r = new int[result.size()];
        for(int i = 0; i < result.size(); i++) {
            r[i] = result.get(i);
        }
        return r;

    }
}
