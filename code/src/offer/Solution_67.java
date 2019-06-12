package offer;
/*二进制中1的个数*/
public class Solution_67 {
    public int NumberOf1(int n) {
        int count = 0;
        while(n!=0){
            count+=1;
            //每做这样的一次与操作，就把这个数的最右边的一个1变成0
            //1000000000000001原码二进制-1，1111111111111111补码 -1，这里应该是原码
            //负数的符号位为1，不算做二进制中1的个数。
            n=n&(n-1);
        }
        return count;
    }
}
