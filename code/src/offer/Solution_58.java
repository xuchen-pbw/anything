package offer;
/*整数中1出现的次数(从1到n，整数中出现1的个数)
        求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？为此他特别数了一下1~13中
        包含1的数字有1、10、11、12、13因此共出现6次,但是对于后面问题他就没辙了。
        ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数
        （从1 到 n 中1出现的次数）。*/
public class Solution_58 {
    public int NumberOf1Between1AndN_Solution(int n) {
        int count=0;
        StringBuffer s=new StringBuffer();
        for(int i=1;i<n+1;i++){
            s.append(i);
        }
        String str=s.toString();
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='1')
                count++;
        }
        return count;

    }
}
