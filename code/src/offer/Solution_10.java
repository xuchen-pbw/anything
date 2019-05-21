package offer;
/*和为S的两个数字
        输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，
        如果有多对数字的和等于S，输出两个数的乘积最小的。*/
import java.util.ArrayList;
public class Solution_10 {
    public ArrayList<Integer> FindNumbersWithSum(int [] array, int sum) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int i = 0;
        int j = array.length - 1;
        if(array == null || j<1)
            return arrayList;
        while (i<j){
            if(array[i]+array[j]==sum){
                arrayList.add(array[i]);
                arrayList.add(array[j]);
                return arrayList;
            }
            if(array[i]+array[j]>sum){
                j--;
            }else {
                i++;
            }
        }
        return arrayList;
    }
}
