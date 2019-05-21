package offer;
/*连续子数组的最大和
        例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。
        给一个数组，返回它的最大连续子序列的和*/
public class Solution_3 {
    public int FindGreatestSumOfSubArray(int[] array) {
        int greatestSum = 0x80000000;
        int sum = 0;
        for(int i=0;i<array.length;i++){
            if(sum<0){
                sum = array[i];
            }else {
                sum = sum + array[i];
            }

            if(sum>greatestSum){
                greatestSum = sum;
            }
        }
        return  greatestSum;
    }
}
