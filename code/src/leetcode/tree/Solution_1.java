package leetcode.tree;
/*minimum-depth-of-binary-tree
        Given a binary tree, find its minimum depth.The minimum depth is the number of nodes
        along the shortest path from the root node down to the nearest leaf node*/
 class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

public class Solution_1 {
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
}
