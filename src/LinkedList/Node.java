/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedList;

/**
 *
 * @author Admin
 */
public class Node<T> {
    T info;
    Node<T> next;

    public Node(T info) {
        this.info = info;
        this.next = null;
    }
}