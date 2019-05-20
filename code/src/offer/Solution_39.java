package offer;
/*反转单词顺序
        “student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
        正确的句子应该是“I am a student.”*/
public class Solution_39 {
    public String ReverseSentence(String str) {
        if(str.trim().equals("")){
            return str;
        }
        String[] a = str.split(" ");
        StringBuffer o = new StringBuffer();
        int i;
        for (i = a.length; i >0;i--){
            o.append(a[i-1]);
            if(i > 1){
                o.append(" ");
            }
        }
        return o.toString();
    }
}
