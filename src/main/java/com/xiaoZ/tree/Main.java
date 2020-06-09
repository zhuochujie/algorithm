package com.xiaoZ.tree;

import com.xiaoZ.tree.printer.BinaryTrees;

public class Main {
    public static void main(String[] args) {
        Integer[] ages = new Integer[]{4,9,2,7,5,1,6};
        BST<Integer> bst = new BST<>();

        for (int i = 0; i < ages.length; i++) {
            bst.add(ages[i]);
        }
        BinaryTrees.print(bst);

        System.out.println();

        bst.remove(9);
        BinaryTrees.print(bst);
    }
}
