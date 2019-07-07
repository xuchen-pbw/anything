package littlecase.Netease;

import java.util.Arrays;
import java.util.Scanner;

public class Solution_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int leng = Integer.valueOf(sc.nextLine()); //int leng = sc.nextInt(); 这个不行
        int[] arrInt = new int[leng];
        String str = sc.nextLine();
        String[] arrStr = str.split(" ");
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<leng;++i){
            arrInt[i] = Integer.valueOf(arrStr[i]);
            min = arrInt[i]>min ? min : arrInt[i];
            max = arrInt[i]<max ? max : arrInt[i];
        }
        if(max == min ) {
            System.out.println("Possible");
            return;
        }
        int d = (max - min)/(leng-1);
        Arrays.sort(arrInt);
        while (leng>1){
           if (arrInt[leng-1] != arrInt[leng-2] + d){
               System.out.println("Impossible");
               return;
           }
            leng--;
        }
        System.out.println("Possible");
        return;
     }
}
