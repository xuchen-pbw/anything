package leetcode.hashtable;
/*Count Primes            prime numbers 素数、质数
        Example:
        Input: 10
        Output: 4
        Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.*/
public class Solution_5 {
    public int countPrimes(int n) {
        if(n <=1 ) return 0;
        //这个数组的大小是n，数组的下标代表了n内的数
        boolean[] notPrime = new boolean[n];
        notPrime[0] = true;
        notPrime[1] = true;

        for(int i = 2; i < Math.sqrt(n); i++){
            if(!notPrime[i]){
                for(int j = 2; j*i < n; j++){
                    notPrime[i*j] = true;
                }
            }
        }

        int count = 0;
        for(int i = 2; i< notPrime.length; i++){
            if(!notPrime[i]) count++;
        }
        return count;
    }
}
