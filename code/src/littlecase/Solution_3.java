package littlecase;
/*输入任意个字符串，将其中的小写字母变为大写，大写字母变为小写，其他字符不用处理；
        输入描述:
        任意字符串：abcd12#%XYZ
        输出描述:
        输出字符串：ABCD12#%xyz*/
import java.util.Scanner;
public class Solution_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        System.out.println(changeStr(str1));
    }
    public static String changeStr(String str){
        str.toUpperCase();  //toUpperCase()返回一个字符串
        char[] ch = str.toCharArray();
        int a = 'A'-'a';   //获得大小写之间差值
        for(int i = 0; i < ch.length; i++){
            if('a' <= ch[i] && ch[i] <= 'z'){
                ch[i] = (char)(ch[i]+a);
            }else if('A' <= ch[i] && ch[i] <= 'Z'){
                ch[i] = (char)(ch[i]-a);
            }
        }
        String s=new String(ch);
        return s;
    }
}
