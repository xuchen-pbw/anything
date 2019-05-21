package offer;
/*构建乘积数组
        给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的
        元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。不能使用除法。*/
public class Solution_65 {
    public int[] multiply(int[] A) {
        int len = A.length;
        int forword[] = new int[len];
        int backword[] = new int[len];
        int B[] = new int[len];
        forword[0] = 1;
        backword[0] = 1;
        for(int i = 1;i < len; i++){
            forword[i] = A[i - 1]*forword[i-1];
            backword[i] = A[len - i]*backword[i - 1];
        }
        for(int i = 0; i < len; i++){
            B[i] = forword[i] * backword[len - i -1];
        }
        return B;

    }

}
