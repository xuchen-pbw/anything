package tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyCollections {
    public static void main(String[] args) {
         MyCollections.test_1();
    }

    public static void  test_1(){
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(-1);
        arrayList.add(3);
        arrayList.add(3);
        arrayList.add(-5);
        arrayList.add(7);
        arrayList.add(4);
        arrayList.add(-9);
        arrayList.add(-7);
        System.out.println("原始数组：");
        System.out.println(arrayList);
        Collections.reverse(arrayList); //传递给它List就行
        System.out.println("Collections.reverse(arrayList)");
        System.out.println(arrayList);

        Collections.rotate(arrayList,4);
        System.out.println(" Collections.rotate(arrayList,4);");
        System.out.println(arrayList);

        Collections.sort(arrayList);
        Collections.shuffle(arrayList);
        Collections.swap(arrayList,2,5);

        Collections.sort(arrayList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
    }

    public static void test_2(){
        ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
        arrayList1.add(-1);
        arrayList1.add(3);
        arrayList1.add(3);
        arrayList1.add(-5);
        arrayList1.add(7);
        arrayList1.add(4);
        arrayList1.add(-9);
        arrayList1.add(-7);
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        arrayList2.add(-3);
        arrayList2.add(-5);
        arrayList2.add(7);

        Collections.max(arrayList1);
        Collections.min(arrayList1);
        Collections.replaceAll(arrayList1,3,-3);
        Collections.frequency(arrayList1,7);
        Collections.indexOfSubList(arrayList1,arrayList2);
        Collections.binarySearch(arrayList1,7);
    }
}
