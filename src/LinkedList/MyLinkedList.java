/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedList;


public class MyLinkedList<T> {

    Node<T> head;
    Node<T> tail;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void addToHead(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    public void addToTail(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void delete(String key) {
        if (head == null) {
            return;
        }

        if (head.info.toString().equals(key)) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
            return;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.info.toString().equals(key)) {
                current.next = current.next.next;
                if (current.next == null) {
                    tail = current;
                }
                return;
            }
            current = current.next;
        }
    }

    public Node<T> search(String key) {
        Node<T> current = head;
        while (current != null) {
            if (current.info.toString().equals(key)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void traverse() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.info + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}

