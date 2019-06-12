package littlecase;
/*大数相乘*/
import java.util.Scanner;
public class Solution_1 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String str1 = sc.next();
            String str2 = sc.next();
            StringBuffer resultStr = bigNumFunction(str1,str2);
            System.out.println(resultStr);
        }
    }
    public static StringBuffer bigNumFunction(String str1,String str2){
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        int[] arrInt1 = new int[arr1.length];
        int[] arrInt2 = new int[arr2.length];
        int[] result = new  int[arr1.length + arr2.length];
        for(int i=0;i<=arr1.length-1;i++){
            arrInt1[i] = arr1[i] - '0';
            //或者 arrInt1[i] = Integer.parseInt(arr1[i]);
        }
        for(int i=0;i<=arr2.length-1;i++){
            arrInt2[i] = arr2[i] - '0';
        }

        for(int i=0;i<=arr1.length-1;i++){
            for(int j=0;j<=arr2.length-1;j++){
                result[i+j] = result[i+j] + arrInt1[i]*arrInt2[j];
            }
        }

        for(int i=result.length-1;i>0;i--){
            if(result[i]/10 >= 1){
                result[i-1] = result[i-1] + result[i]/10;
                result[i] = result[i] % 10;
            }
        }

        StringBuffer sb = new StringBuffer();
        for(int i=0;i<result.length-1;i++){
            sb.append(result[i]);
        }
        return sb;
    }

}