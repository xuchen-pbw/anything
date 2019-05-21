package offer;
/*从上往下打印出二叉树的每个节点，同层节点从左至右打印。*/
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Solution_45{
        public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
            ArrayList<Integer> res = new ArrayList<Integer>();
            if(root==null) return res;
            Queue<TreeNode> queue = new LinkedList<TreeNode>();
            queue.add(root);
            while(queue.size() != 0){
                root = queue.remove();
                res.add(root.val);
                if(root.left != null) queue.add(root.left);
                if(root.right != null) queue.add(root.right);
            }
            return res;
        }
}
