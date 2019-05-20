package offer;
/*矩阵覆盖
        我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
        请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？*/

public class Solution_20 {
    public int RectCover(int target) {
        //矩阵的大小为  2*target
        if (target <= 0) return 0;
        if (target == 1) return 1;
        if (target == 2) return 2;
        int m = 1;
        int n = 2;
        for (int i = 2; i < target; i++) {
            n = n + m;
            m = n - m;
        }
        return n;
    }
}
