package offer;
/*数组中只出现一次的数字
        一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。*/
import java.util.ArrayList;
import java.util.Arrays;
public class Solution_7 {
    //num1,num2分别为长度为1的数组。传出参数
    // 将num1[0],num2[0]设置为返回结果
    public class Solution {
        public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            Arrays.sort(array);
            for(int i=0;i<array.length;i++){
                if((i+1)<array.length && array[i]==array[i+1]){
                    i++;
                }else {
                    arrayList.add(array[i]);
                }
            }
            num1[0] = arrayList.get(0);
            num2[0] = arrayList.get(1);

        }
    }
}
