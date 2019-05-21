package offer;
/*二叉树中和为某一值得路径
        输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
        路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。(注意:
        在返回值的list中，数组长度大的数组靠前)*/
import java.util.ArrayList;
public class Solution_47 {
    ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> temp = new ArrayList<Integer>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if(root==null) return res;
        target = target - root.val;
        temp.add(root.val);
        if(target==0 && root.left==null && root.right==null ){
            res.add(new ArrayList<Integer>(temp));
        }else{
            FindPath(root.left,target);
            FindPath(root.right,target);
        }
        temp.remove(temp.size()-1);
        return res;
    }
}
