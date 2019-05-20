package offer;
/*替换空格
请实现一个函数，将一个字符串中的每个空格替换成“%20”。
例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。*/
public class Solution_12 {
    public String replaceSpace(StringBuffer str) {
        StringBuffer ref = new StringBuffer();
        int len = str.length()-1;

        for(int i=len;i>=0;i--){
            if(str.charAt(i)==' ')
                ref.append("02%");
            else
                ref.append(str.charAt(i));
        }

        return ref.reverse().toString();

    }
}
