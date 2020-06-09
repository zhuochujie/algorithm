package com.xiaoZ.tree;

import java.util.Comparator;

public class BST<E> extends BinaryTree<E> {

    private Comparator<E> comparator;

    public BST() {
        this(null);
    }

    public BST(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E element){
        elementNotNullCheck(element);
        if (root == null){
            root = new TreeNode<E>(element,null);
            size++;
            //添加新节点之后的处理
            afterAdd(root);
            return;
        }
        //保存父节点
        TreeNode<E> parent = root;
        //从跟节点开始找
        TreeNode<E> node = root;
        Integer cmp = 0;
        do {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0){
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }else {
                return;
            }
        } while (node != null);

        TreeNode<E> newNode = new TreeNode<E>(element, parent);
        if (cmp > 0){
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;
        //添加新节点之后的处理
        afterAdd(newNode);
    }

    //添加node之后的调整
    protected void afterAdd(TreeNode<E> node){};

    public Boolean contains(E element) {
        return treeNode(element) != null;
    }

    public void remove(E element) {
        remove(treeNode(element));
    }

    private TreeNode<E> treeNode(E element) {
        TreeNode<E> node = root;
        while (node != null) {
            Integer cmp = compare(node.element, element);
            if (cmp == 0) {
                return node;
            }else if (cmp > 0) {
                node = node.left;
            }else {
                node = node.right;
            }
        }
        return null;
    }

    private void remove(TreeNode<E> node) {
        if (node == null) {
            return;
        }

        size--;
        if (node.hasTwoChildren()) {
            //找到后继节点
            TreeNode<E> s = successor(node);
            //用后继节点的值替换node的值
            node.element = s.element;
            //将node指向后继节点的位置,这个时候要删的节点度也只会是0或1,可复用下面的代码
            node = s;
        }
        //删除node节点
        TreeNode<E> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { //node是度为一的节点
            if (node.parent == null) {
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            }else {
                node.parent.right = replacement;
            }
        } else if (node.parent == null) { //node是叶子节点并且是根节点
            root = null;
        } else {
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
    }

    private void elementNotNullCheck(E element){
        if (element == null){
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private Integer compare(E e1,E e2){
        if (comparator != null) {
            return comparator.compare(e1,e2);
        }
        return ((Comparable)e1).compareTo(e2);
    }

}
