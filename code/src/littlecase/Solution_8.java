package littlecase;

import java.util.*;

public class Solution_8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        Solution_8 test = new Solution_8();
        test.solve(str);
    }

    private void solve(String str) {
        str = str.toLowerCase();
        int[] nums = new int[26];
        for (char c:str.toCharArray()) {
            nums[c-'a']++;
        }
        char []order = new char[]{'z','w','u','x','g','r','s','o','v','i'};
        Map<Character, String> map = new HashMap<>();
        map.put('z', "zero");
        map.put('w', "two");
        map.put('u', "four");
        map.put('x', "six");
        map.put('g', "eight");
        map.put('r', "three");
        map.put('s', "seven");
        map.put('o', "one");
        map.put('v', "five");
        map.put('i', "nine");
        Map<Character, Integer> map2num = new HashMap<>();
        map2num.put('z', 0);
        map2num.put('w', 2);
        map2num.put('u', 4);
        map2num.put('x', 6);
        map2num.put('g', 8);
        map2num.put('r', 3);
        map2num.put('s', 7);
        map2num.put('o', 1);
        map2num.put('v', 5);
        map2num.put('i', 9);
        List<Integer> list = new ArrayList<>();
        int i=0;
        while (i<10){
            char c = order[i];
            while (nums[c-'a']>0){
                for (char t:map.get(c).toCharArray()) {
                    nums[t-'a']--;
                }
                list.add(map2num.get(c));
            }
            i++;
        }
        int []res = new int[list.size()];
        for (int j=0;j<list.size();j++) {
            res[j] = list.get(j);
        }
        Arrays.sort(res);
        for (int n:res) {
            System.out.print(n);
        }
    }
}
