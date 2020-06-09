package com.xiaoZ.tree.leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class _226_翻转二叉树 {
    //前序遍历
    public TreeNode invertTree1(TreeNode root){
        if (root == null) {
            return null;
        }

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        invertTree1(root.left);
        invertTree1(root.right);

        return root;
    }

    //后序遍历
    public TreeNode invertTree2(TreeNode root){
        if (root == null) {
            return null;
        }

        invertTree2(root.left);
        invertTree2(root.right);

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        return root;
    }

    //中序遍历
    public TreeNode invertTree3(TreeNode root){
        if (root == null) {
            return null;
        }

        invertTree3(root.left);

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        //因为在这之前已经将left和right交换,所以现在的left就是之前的right
        invertTree3(root.left);

        return root;
    }

    //层序遍历
    public TreeNode invertTree4(TreeNode root){
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return root;
    }
}
