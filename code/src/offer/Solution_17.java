package offer;
/*斐波那契数列
        现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
        n<=39。F(n)=F(n-1)+F(n-2) */

public class Solution_17 {
    public int Fibonacci(int n) {
        if(n==0 || n==1)
            return n;
        int fn1 = 0;
        int fn2 = 1;
        for(int i=2;i<=n;i++){
            fn2 = fn2 + fn1;
            fn1 = fn2 - fn1;
        }
        return fn2;
    }
}
