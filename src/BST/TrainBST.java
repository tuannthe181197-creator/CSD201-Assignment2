package BST;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TrainBST {
    private TrainNode root;

    public TrainBST(){}

    public boolean isEmpty(){ return root == null; }

    /** Insert theo tcode (duy nhất). Trả về false nếu trùng hoặc input null/invalid. */
    public boolean insert(Train x){
        if (x == null) return false;
        x.validate(); // đảm bảo hợp lệ trước khi chèn (ném IllegalArgumentException nếu sai)
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

    /** Search theo tcode (null/blank → trả null) */
    public TrainNode search(String tcode){
        if (tcode == null || tcode.trim().isEmpty()) return null;
        String k = tcode.trim();
        TrainNode p = root;
        while(p != null){
            int cmp = k.compareToIgnoreCase(p.info.getTcode());
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

    // Breadth-first traverse
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

    /** Delete theo tcode (copying). Trả false nếu không tìm thấy/invalid key. */
    public boolean deleteByTcode(String tcode){
        if (tcode == null || tcode.trim().isEmpty()) return false;
        String key = tcode.trim();

        TrainNode p = root, f = null;
        while (p != null){
            int cmp = key.compareToIgnoreCase(p.info.getTcode());
            if (cmp == 0) break;
            f = p;
            p = (cmp < 0) ? p.left : p.right;
        }
        if (p == null) return false; // not found

        // case: two children → tìm successor bên phải và copy info
        if (p.left != null && p.right != null){
            TrainNode q = p.right; TrainNode fq = p;
            while (q.left != null){ fq = q; q = q.left; }
            p.info = q.info; // copy info
            p = q; f = fq;   // xóa q ở dưới
        }
        // p còn tối đa 1 con
        TrainNode child = (p.left != null) ? p.left : p.right;
        if (f == null) root = child;
        else if (f.left == p) f.left = child;
        else f.right = child;
        return true;
    }

    /** Cân bằng đơn giản: đưa về mảng đã sort theo in-order rồi build lại */
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

    /** Load từ file: báo cáo chi tiết số dòng thêm/trùng/lỗi định dạng */
    public int loadFromFile(String file) {
        if (file == null || file.trim().isEmpty()) {
            System.err.println("File path is empty.");
            return 0;
        }
        File f = new File(file);
        if (!f.exists() || !f.isFile()) {
            System.err.println("File not found: " + file);
            return 0;
        }

        int added = 0, skippedDup = 0, skippedInvalid = 0, lineNo = 0;
        try (Scanner sc = new Scanner(f, StandardCharsets.UTF_8.name())) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                lineNo++;
                String raw = (line == null ? "" : line.trim());
                if (raw.isEmpty() || raw.startsWith("#")) continue;
                try {
                    Train t = Train.parse(raw);
                    boolean ok = insert(t);
                    if (ok) added++; else skippedDup++;
                } catch (IllegalArgumentException ex) {
                    skippedInvalid++;
                    System.err.printf("Line %d invalid: %s%n", lineNo, ex.getMessage());
                } catch (Exception ex) {
                    skippedInvalid++;
                    System.err.printf("Line %d error: %s%n", lineNo, ex.toString());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open file: " + e.getMessage());
            return 0;
        } catch (SecurityException e) {
            System.err.println("No permission to read file: " + e.getMessage());
            return 0;
        }
        System.out.printf("Loaded: %d, Duplicates: %d, Invalid: %d%n", added, skippedDup, skippedInvalid);
        return added;
    }

    /** Lưu in-order ra file, tự tạo thư mục cha nếu chưa tồn tại */
    public void saveInorderToFile(String file){
        if (file == null || file.trim().isEmpty()) {
            System.err.println("Output path is empty.");
            return;
        }
        File out = new File(file.trim());
        File parent = out.getParentFile();
        try {
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                System.err.println("Cannot create directory: " + parent.getAbsolutePath());
            }
        } catch (SecurityException se) {
            System.err.println("No permission to create directory: " + se.getMessage());
        }

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8))) {
            saveInorder(root, pw);
            System.out.println("Written in-order list to: " + out.getPath());
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open output file: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("No permission to write file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error while writing: " + e.getMessage());
        }
    }

    private void saveInorder(TrainNode p, PrintWriter pw){
        if (p == null) return;
        saveInorder(p.left, pw);
        pw.println(p.info.toDataLine());
        saveInorder(p.right, pw);
    }
}
