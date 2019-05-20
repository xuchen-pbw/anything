package leetcode.solution;
/*max-points-on-a-line
        Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.*/
import java.util.HashMap;
  class Point {
      int x;
      int y;
      Point() { x = 0; y = 0; }
      Point(int a, int b) { x = a; y = b; }
  }
public class Solution_1 {
    public int maxPoints(Point[] points) {
        int n = points.length;
        if(n<2) return n;

        int ret = 0;
        for(int i=0;i<n;i++){
            // 分别统计与点i重合以及垂直的点的个数
            int duplication = 1, vtl = 0;
            HashMap<Float,Integer> map = new HashMap<Float,Integer>();
            Point a = points[i];
            for(int j=0;j<n;j++){
                //continue只用于循环  表达结束本次循环
                if(i==j) continue;
                Point b = points[j];
                if(a.x==b.x){
                    if(a.y==b.y) duplication++;
                    else vtl++;
                }else{
                    float k = (float)(a.y-b.y)/(a.x-b.x);
                    if(map.get(k)==null) map.put(k,1);
                    else map.put(k, map.get(k)+1);
                }
            }
            int max = vtl;
            //map.keySet() 将map集合中的键以set列表形式返回
            for(float k: map.keySet()){
                max = Math.max(max,map.get(k));
            }
            ret = Math.max(ret,max+duplication);
        }
        return ret;
    }
}
