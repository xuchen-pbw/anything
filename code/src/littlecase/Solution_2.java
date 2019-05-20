package littlecase;
/*某国货币系统包含面值1元，4元，16元，64元共计4种硬币，以及面值1024元的纸币。
        现在某人使用1024元的纸币购买了一件价格为N(0≤N≤1024)的商品。
        请问最少他会收到多少枚硬币?
        求解思路：贪心算法*/

import java.util.Scanner;

public class Solution_2 {
    public void leastCoins(){
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        Scanner sc = new Scanner(System.in);
        int n =  sc.nextInt();
        n = 1024 - n ;

        count4 = n/64;
        n = n%64;
        count3 = n/16;
        n = n%16;
        count2 = n/4;
        n = n%4;
        count1 = n/1;
        System.out.println(count1+count2+count3+count4);
    }
}
