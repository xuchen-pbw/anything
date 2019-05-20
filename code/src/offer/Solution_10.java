package offer;

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
