package com.xiaoZ.tree;

import com.xiaoZ.tree.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<E> implements BinaryTreeInfo {
    protected Integer size = 0;
    protected TreeNode<E> root;

    public boolean isEmpty(){
        return size == 0;
    }

    public Integer size(){
        return size;
    }

    protected class TreeNode<E> {
        E element;
        TreeNode<E> left;
        TreeNode<E> right;
        TreeNode<E> parent;

        public TreeNode(E element,TreeNode<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf(){
            return left == null && right == null;
        }

        public boolean hasTwoChildren(){
            return left != null && right != null;
        }
    }

    public void preorderTraversal(BST.Visitor<E> visitor){
        if (visitor == null) {
            throw new IllegalArgumentException("visitor must not be null");
        }
        preorderTraversal(root,visitor);
    }

    //前序遍历,中序和后续改变输出顺序即可
    private void preorderTraversal(TreeNode<E> node, BST.Visitor<E> visitor){
        if (node == null || visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.element);
        preorderTraversal(node.left,visitor);
        preorderTraversal(node.right,visitor);
    }

    public void inorderTraversal(BST.Visitor<E> visitor){
        if (visitor == null) {
            throw new IllegalArgumentException("visitor must not be null");
        }
        inorderTraversal(root,visitor);
    }

    //中序遍历
    private void inorderTraversal(TreeNode<E> node, BST.Visitor<E> visitor){
        if (node == null || visitor.stop) {
            return;
        }
        inorderTraversal(node.left,visitor);
        if (visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.element);
        inorderTraversal(node.right,visitor);
    }

    public void postorderTraversal(BST.Visitor<E> visitor){
        if (visitor == null) {
            throw new IllegalArgumentException("visitor must not be null");
        }
        postorderTraversal(root,visitor);
    }

    //后续遍历
    private void postorderTraversal(TreeNode<E> node, BST.Visitor<E> visitor){
        if (node == null || visitor.stop) {
            return;
        }
        postorderTraversal(node.left,visitor);
        postorderTraversal(node.right,visitor);
        if (visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.element);
    }

    //层序遍历,使用队列实现
    public void levelOrderTraversal(BST.Visitor<E> visitor){
        if (root == null || visitor == null) {
            throw new IllegalArgumentException("root and visitor must not be null");
        }
        Queue<TreeNode<E>> queue = new LinkedList();
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode<E> node = queue.poll();
            if (visitor.visit(node.element)) {
                return;
            }
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
    }

    private TreeNode<E> predecessor(TreeNode<E> node) {
        if (node == null) {
            return null;
        }
        //左子树不为null
        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        //节点的左子树为null并且父节点不为null
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        return node.parent;
    }

    protected TreeNode<E> successor(TreeNode<E> node) {
        if (node == null) {
            return null;
        }
        //左子树不为null
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        //节点的左子树为null并且父节点不为null
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        return node.parent;
    }

    public int height(){
        return height1(root);
    }

    //递归实现
    private int height1(TreeNode<E> node){
        if (node == null) {
            return 0;
        }
        return Math.max(height1(node.left),height1(node.right)) + 1;
    }

    private int height2(){
        if (root == null) {
            return 0;
        }
        Queue<TreeNode<E>> queue = new LinkedList();
        //层数
        int height = 0;
        //每一层的节点数
        int levelSize = 1;
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode<E> node = queue.poll();
            levelSize--;
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
            if (levelSize == 0){
                levelSize = queue.size();
                height++;
            }
        }
        return height;
    }

    /*public boolean isComplete(){
        if (root == null) {
            return false;
        }
        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()){
            TreeNode<E> node = queue.poll();

            if (leaf && !node.isLeaf()) {
                return false;
            }

            if (node.hasTwoChildren()) {
                queue.offer(node.left);
                queue.offer(node.right);
            }else if (node.left == null && node.right != null) {
                return false;
            }else {
                if (node.left != null) {
                    queue.offer(node.left);
                }
                leaf = true;
            }
        }
        return true;
    }*/

    public boolean isComplete(){
        if (root == null) {
            return false;
        }
        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.offer(root);
        Boolean leaf = false;

        while (!queue.isEmpty()) {
            TreeNode<E> node = queue.poll();

            if (leaf && !node.isLeaf()) {
                return false;
            }

            if (node.left != null) {
                queue.offer(node.left);
            }else if (node.right != null) {
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            }else {
                leaf = true;
            }
        }
        return true;
    }

    public static abstract class Visitor<E> {
        boolean stop;
        abstract boolean visit(E element);
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((TreeNode)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((TreeNode)node).right;
    }

    @Override
    public Object string(Object node) {
        return ((TreeNode)node).element;
    }
}
