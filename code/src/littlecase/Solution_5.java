package littlecase;
/*控制台输入一个 n*m 的矩阵 存储在一个n*m的数组里*/
import java.util.Scanner;

public class Solution_5 {
    private String getHL;
    private String[] HLs;
    private int[] HL;
    public static void main(String[] args) {
        System.out.println(new Solution_5().get());
    }

    public int[][] get(){
        /**
         * 创建数组行列数
         */
        HL = new int[2];
        Scanner get = new Scanner(System.in);
        System.out.println("请问想输入矩阵的规格是？(输入格式：行x列,例如5x5)");
        getHL =get.nextLine();
        HLs =getHL.split("x");
        for (int i = 0;i <2;i++){
            HL[i] = Integer.valueOf(HLs[i]);
        }
        int[][] array = new int[HL[0]][HL[1]];

        /**
         * 初始化数组
         */
        for (int i = 0;i < HL[0];i++){
            String temp = get.nextLine();
            String[] temps = temp.split(" +");
            for (int j = 0;j < HL[1];j++){
                array[i][j]=Integer.valueOf(temps[j]);
            }
        }
        return array;
    }

}
