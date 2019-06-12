package leetcode;
/*Factorial Trailing Zeroes  阶乘中零的个数，所有的0都是有2*5=10得来的，2这个因子很好分解得来
                             因此，有多少5因子(5+5+5+5+......)*(2+2+......)，就能产生多少个0;
        This question is pretty straightforward.
        Because all trailing 0 is from factors 5 * 2.
        But sometimes one number may have several 5 factors, for example,
        25 have two 5 factors, 125 have three 5 factors. In the n! operation,
        factors 2 is always ample. So we just count how many 5 factors in all number
        from 1 to n.*/
public class Solution_5 {
    public int trailingZeroes(int n) {
        return n == 0 ? 0 : n / 5 + trailingZeroes(n / 5);
    }
}
