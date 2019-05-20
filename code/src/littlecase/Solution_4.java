package littlecase;
/*小偷来到了一个神秘的王宫，突然眼前一亮，发现5个宝贝，每个宝贝的价值都不一样，且重量也不一样，但是小偷的背包携带重量
        有限，所以他不得不在宝贝中做出选择，才能使偷到的财富最大，请你帮助小偷计算一下。

        输入描述:
        宝贝价值：6,3,5,4,6
        宝贝重量：2,2,6,5,4
        小偷背包容量：10
        输出描述:
        偷到宝贝的总价值：15

        示例1
        输入
        6,3,5,4,6
        2,2,6,5,4
        10
        输出
        15*/
import java.util.Scanner;

public class Solution_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = sc.next();
        String str2 = sc.next();
        String str3 = sc.next();
        int[] value = transform(str1);
        int[] weight = transform(str2);
        int[] bag = transform(str3);
        sc.close();
        int m = bag[0];
        int n = value.length;
        int w[] = weight;
        int p[] = value;
        int c[][] = BackPack_Solution(m, n, w, p);
        System.out.println(c[c.length-1][c[0].length-1]);
    }

    public static int[][] BackPack_Solution(int m, int n, int[] w, int[] p) {
        //c[i][v]表示前i件物品恰放入一个重量为m的背包可以获得的最大价值
        int c[][] = new int[n + 1][m + 1];
        for (int i = 0; i < n + 1; i++)
            c[i][0] = 0;
        for (int j = 0; j < m + 1; j++)
            c[0][j] = 0;

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                //当物品为i件重量为j时，如果第i件的重量(w[i-1])小于重量j时，c[i][j]为下列两种情况之一：
                //(1)物品i不放入背包中，所以c[i][j]为c[i-1][j]的值
                //(2)物品i放入背包中，则背包剩余重量为j-w[i-1],所以c[i][j]为c[i-1][j-w[i-1]]的值加上当前物品i的价值
                if (w[i - 1] <= j) {
                    if (c[i - 1][j] < (c[i - 1][j - w[i - 1]] + p[i - 1]))
                        c[i][j] = c[i - 1][j - w[i - 1]] + p[i - 1];
                    else
                        c[i][j] = c[i - 1][j];
                } else
                    c[i][j] = c[i - 1][j];
            }
        }
        return c;
    }
    //字符串转成字符串数组  最后转成数字数组
    private static int[] transform(String str1) {
        String[] split = str1.split("#");  //
        String[] split2 = split[split.length-1].split(",");
        int[] value=new int[split2.length ];
        for (int i = 0; i < value.length; i++) {
            value[i]=Integer.parseInt(split2[i]);
        }
        return value;
    }

}
