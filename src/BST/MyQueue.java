/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BST;

/**
 *
 * @author ADMIN
 */
public class MyQueue<E> {
    private static class Node<E> {
        E val; Node<E> next;
        Node(E v){ this.val = v; }
    }
    private Node<E> head, tail;
    private int size = 0;

    public boolean isEmpty(){ return size == 0; }
    public int size(){ return size; }

    public void enqueue(E v){
        Node<E> n = new Node<>(v);
        if(tail == null){ head = tail = n; }
        else { tail.next = n; tail = n; }
        size++;
    }

    public E dequeue(){
        if(isEmpty()) return null;
        Node<E> n = head;
        head = head.next;
        if(head == null) tail = null;
        size--;
        return n.val;
    }
}
