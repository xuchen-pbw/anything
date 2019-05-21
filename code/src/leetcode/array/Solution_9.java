package leetcode.array;
/*Merge Sorted Array
        Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
        Example:
        Input:
        nums1 = [1,2,3,0,0,0], m = 3
        nums2 = [2,5,6],       n = 3
        Output: [1,2,2,3,5,6]*/
public class Solution_9 {
    public void merge(int[] A, int m, int[] B, int n) {
        int i=m-1, j=n-1, k=m+n-1;
        while (i>-1 && j>-1) A[k--]= (A[i]>B[j]) ? A[i--] : B[j--];
        while (j>-1)         A[k--]=B[j--];
    }
}
