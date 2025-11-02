/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BST;

public class TestBST {
    public static void main(String[] args) {
        MyBST<Integer> tree = new MyBST<>();

        tree.insert(40);
        tree.insert(20);
        tree.insert(60);
        tree.insert(10);
        tree.insert(30);
        tree.insert(50);
        tree.insert(70);

        System.out.print("In-order: ");
        tree.inOrder();  // 10 20 30 40 50 60 70

        System.out.println("\nSearch 30: " + (tree.search(30) != null));
        System.out.println("Delete 20");
        tree.delete(20);

        System.out.print("In-order after delete: ");
        tree.inOrder();  // 10 30 40 50 60 70
    }
}

