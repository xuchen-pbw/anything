package leetcode.tree;

import java.util.ArrayDeque;
import java.util.Queue;

/*二叉树的深度和宽度*/
public class Solution_2 {
    public static int getMaxDepth(TreeNode root) {
        if (root == null)
            return 0;
        else {
            int left = getMaxDepth(root.left);
            int right = getMaxDepth(root.right);
            return 1 + Math.max(left, right);
        }
    }


    //获取最短深度
    public int run(TreeNode root) {
        //该节点为空
        if(root==null) return 0;
        //该节点无双子树
        if(root.left==null && root.right==null) return 1;
        //该节点仅有单子树
        if(root.left==null || root.right==null)
            return Math.max(run(root.left),run(root.right))+1;

        return Math.min(run(root.left),run(root.right))+1;
    }

    // 获取最大宽度
    public static int getMaxWidth(TreeNode root) {
        if (root == null)
            return 0;

        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        int maxWitdth = 1; // 最大宽度
        queue.add(root); // 入队

        while (true) {
            int len = queue.size(); // 当前层的节点个数
            if (len == 0)
                break;
            while (len > 0) {// 如果当前层，还有节点
                TreeNode t = queue.poll();
                len--;
                if (t.left != null)
                    queue.add(t.left); // 下一层节点入队
                if (t.right != null)
                    queue.add(t.right);// 下一层节点入队
            }
            maxWitdth = Math.max(maxWitdth, queue.size());
        }
        return maxWitdth;
    }
}
