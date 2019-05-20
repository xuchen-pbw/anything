package offer;

public class Problem_3 {
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
