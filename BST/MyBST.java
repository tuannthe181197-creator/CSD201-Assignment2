/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BST;

public class MyBST<T extends Comparable<T>> {
    BSTNode<T> root;

    public MyBST() {
        root = null;
    }

    // Thêm phần tử
    public void insert(T x) {
        root = insertRec(root, x);
    }

    private BSTNode<T> insertRec(BSTNode<T> node, T x) {
        if (node == null) return new BSTNode<>(x);
        if (x.compareTo(node.info) < 0) node.left = insertRec(node.left, x);
        else if (x.compareTo(node.info) > 0) node.right = insertRec(node.right, x);
        return node;
    }

    // Duyệt theo thứ tự LNR
    public void inOrder() { inOrderRec(root); }

    private void inOrderRec(BSTNode<T> node) {
        if (node == null) return;
        inOrderRec(node.left);
        System.out.print(node.info + " ");
        inOrderRec(node.right);
    }

    // Tìm kiếm
    public BSTNode<T> search(T x) {
        BSTNode<T> cur = root;
        while (cur != null) {
            if (x.compareTo(cur.info) == 0) return cur;
            cur = (x.compareTo(cur.info) < 0) ? cur.left : cur.right;
        }
        return null;
    }

    // Xóa (đơn giản)
    public void delete(T x) { root = deleteRec(root, x); }

    private BSTNode<T> deleteRec(BSTNode<T> node, T x) {
        if (node == null) return null;
        if (x.compareTo(node.info) < 0) node.left = deleteRec(node.left, x);
        else if (x.compareTo(node.info) > 0) node.right = deleteRec(node.right, x);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            node.info = minValue(node.right);
            node.right = deleteRec(node.right, node.info);
        }
        return node;
    }

    private T minValue(BSTNode<T> node) {
        while (node.left != null) node = node.left;
        return node.info;
    }
}

