package offer;
/*顺时针打印矩阵
        输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下4 X 4矩阵：
        1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.*/
import java.util.ArrayList;

public class Solution_4
{
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(row==0&&col==0) return res;
        int top = 0;   int down = row - 1;
        int left = 0;  int right = col - 1;
        while(top<=down && left<=right){
            for(int i=left;i<=right;i++)
                res.add(matrix[top][i]);
            for(int i=top+1;i<=down;i++)
                res.add(matrix[i][right]);
            if(top != down){
                for(int i=right-1;i>=left;i--)
                    res.add(matrix[down][i]);
            }
            if(left != right){
                for(int i=down-1;i>top;i--)
                    res.add(matrix[i][left]);
            }
            top++; down--;
            left++; right--;
        }
        return res;
    }
}
