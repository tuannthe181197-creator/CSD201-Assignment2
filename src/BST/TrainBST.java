/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BST;

/**
 *
 * @author ADMIN
 */
import java.io.*;
import java.util.Scanner;

public class TrainBST {
    private TrainNode root;

    public TrainBST(){}

    public boolean isEmpty(){ return root == null; }

    // Insert by tcode (unique). Return false if duplicate.
    public boolean insert(Train x){
        if (x == null) return false;
        if (root == null){ root = new TrainNode(x); return true; }
        TrainNode f = null, p = root;
        String key = x.getTcode();
        while(p != null){
            f = p;
            int cmp = key.compareToIgnoreCase(p.info.getTcode());
            if (cmp == 0) return false; // duplicate
            p = (cmp < 0) ? p.left : p.right;
        }
        int cmp = key.compareToIgnoreCase(f.info.getTcode());
        if (cmp < 0) f.left = new TrainNode(x);
        else f.right = new TrainNode(x);
        return true;
    }

    // Search by tcode
    public TrainNode search(String tcode){
        TrainNode p = root;
        while(p != null){
            int cmp = tcode.compareToIgnoreCase(p.info.getTcode());
            if (cmp == 0) return p;
            p = (cmp < 0) ? p.left : p.right;
        }
        return null;
    }

    // In-order traversal (LNR)
    public void inorder(){
        System.out.println("tcode  | train_name        |  seat | booked |  dtime | depart_place");
        System.out.println("-------+--------------------+-------+--------+--------+--------------");
        inorder(root);
    }
    private void inorder(TrainNode p){
        if (p == null) return;
        inorder(p.left);
        System.out.println(p.info.toString());
        inorder(p.right);
    }

    // Count
    public int count(){ return count(root); }
    private int count(TrainNode p){
        if (p == null) return 0;
        return 1 + count(p.left) + count(p.right);
    }

    // Breadth-first traverse (using our queue)
    public void breadthFirst(){
        System.out.println("tcode  | train_name        |  seat | booked |  dtime | depart_place");
        System.out.println("-------+--------------------+-------+--------+--------+--------------");
        if (root == null) return;
        MyQueue<TrainNode> q = new MyQueue<>();
        q.enqueue(root);
        while(!q.isEmpty()){
            TrainNode n = q.dequeue();
            System.out.println(n.info.toString());
            if (n.left != null) q.enqueue(n.left);
            if (n.right != null) q.enqueue(n.right);
        }
    }

    // Delete by tcode using copying
    public boolean deleteByTcode(String tcode){
        TrainNode p = root, f = null;
        while (p != null){
            int cmp = tcode.compareToIgnoreCase(p.info.getTcode());
            if (cmp == 0) break;
            f = p;
            p = (cmp < 0) ? p.left : p.right;
        }
        if (p == null) return false; // not found

        // case: two children
        if (p.left != null && p.right != null){
            // find min on right (successor)
            TrainNode q = p.right; TrainNode fq = p;
            while (q.left != null){ fq = q; q = q.left; }
            p.info = q.info; // copy info
            // Now delete q
            p = q; f = fq;
        }
        // now p has at most one child
        TrainNode child = (p.left != null) ? p.left : p.right;
        if (f == null) root = child;
        else if (f.left == p) f.left = child;
        else f.right = child;
        return true;
    }

    // Balance (simple): convert to array (in-order), rebuild
    public void balance(){
        int n = count();
        if (n <= 1) return;
        Train[] arr = new Train[n];
        Index idx = new Index();
        fillToArray(root, arr, idx);
        root = buildBalanced(arr, 0, n - 1);
    }

    private static class Index { int v = 0; }

    private void fillToArray(TrainNode p, Train[] a, Index idx){
        if (p == null) return;
        fillToArray(p.left, a, idx);
        a[idx.v++] = p.info;
        fillToArray(p.right, a, idx);
    }

    private TrainNode buildBalanced(Train[] a, int l, int r){
        if (l > r) return null;
        int m = (l + r) >>> 1;
        TrainNode node = new TrainNode(a[m]);
        node.left = buildBalanced(a, l, m - 1);
        node.right = buildBalanced(a, m + 1, r);
        return node;
    }

    // Load data from file (append to current tree)
    public int loadFromFile(String file) {
        int added = 0, skipped = 0;
        try (Scanner sc = new Scanner(new File(file), "UTF-8")) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                try {
                    Train t = Train.parse(line);
                    if (insert(t)) added++; else skipped++;
                } catch (Exception ex) {
                    skipped++;
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot read file: " + e.getMessage());
        }
        System.out.printf("Loaded: %d, Skipped: %d%n", added, skipped);
        return added;
    }

    // Save in-order to file
    public void saveInorderToFile(String file){
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            saveInorder(root, pw);
            System.out.println("Written in-order list to: " + file);
        } catch (IOException e) {
            System.err.println("Cannot write file: " + e.getMessage());
        }
    }
    private void saveInorder(TrainNode p, PrintWriter pw){
        if (p == null) return;
        saveInorder(p.left, pw);
        pw.println(p.info.toDataLine());
        saveInorder(p.right, pw);
    }
}
