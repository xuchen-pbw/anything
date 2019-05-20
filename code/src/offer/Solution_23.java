package offer;
/*调整数值顺序使得奇数位于偶数前面
输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变*/

import java.util.Vector;
public class Solution_23 {
    public void reOrderArray(int [] array) {
        Vector<Integer> odd = new Vector<Integer>();
        Vector<Integer> even = new Vector<Integer>();
        for(int i=0;i<array.length;i++){
            if((array[i]%2)==0){
                even.add(array[i]);
            }else{
                odd.add(array[i]);
            }
        }

        odd.addAll(even);
        for(int i=0;i<array.length;i++){
            array[i] = odd.get(i);
        }
    }
}
