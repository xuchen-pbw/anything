package com.lynch;

import java.util.ArrayList;

/**
 * Created by lynch on 2018/11/22. <br>
 **/
public class SwitchCount {

    public static void main(String[] args) {
        int[] cnt = {1,2,3};
        switchtest(cnt);
    }

    public static void switchtest(int[] cnt) {
        ArrayList<Integer> intcount = new ArrayList<>();
        for (int i = 0; i < cnt.length; i++) {
            switch (cnt[i]) {
                case 0:
                    intcount.add(0);
                    break;
                case 1:
                    intcount.add(1);
                    break;
                case 2:
                    intcount.add(2);
                    break;
                case 3:
                    intcount.add(3);
                default:
                    break;
            }
        }
        for (int i = 0; i < intcount.size(); i++) {
            System.out.println(intcount.get(i));
        }
        System.out.println(intcount.size());
    }
}
