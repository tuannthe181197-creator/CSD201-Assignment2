/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BST;

/**
 *
 * @author Admin
 */
public class BSTNode<T> {
    T info;
    BSTNode<T> left, right;

    public BSTNode(T info) {
        this.info = info;
        left = right = null;
    }
}
