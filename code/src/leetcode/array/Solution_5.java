package leetcode.array;
/*Plus One
        Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
        The digits are stored such that the most significant digit is at the head of the list,
        and each element in the array contain a single digit.
        Input: [1,2,3]
        Output: [1,2,4]
        Explanation: The array represents the integer 123.*/
public class Solution_5 {
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        for(int i=n-1; i>=0; i--) {
            if(digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            digits[i] = 0;
        }

        int[] newNumber = new int [n+1];
        newNumber[0] = 1;

        return newNumber;

    }
}
