package LinkedList;

/*
 *
 * @author Duyenhtmhe187421
 */

import java.io.*;
import java.util.Scanner;

// CustomerList kế thừa MyLinkedList<Customer>, quản lý danh sách khách hàng
public class CustomerList extends MyLinkedList<Customer> {

    // ====== 1. Load dữ liệu từ file ======
    public void loadFromFile(String fname) {
        head = null;  // reset danh sách
        tail = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
            // Mở file fname bằng BufferedReader để đọc dữ liệu
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                // Đọc từng dòng trong file
                String[] parts = line.split("\\|");
                // Tách dữ liệu theo ký tự "|" thành 3 phần: code, name, phone
                if (parts.length == 3) {
                    String code = parts[0].trim();   // Loại bỏ khoảng trắng
                    String name = parts[1].trim();
                    String phone = parts[2].trim();

                    addToTail(new Customer(code, name, phone));
                    // Thêm khách hàng hợp lệ vào cuối danh sách
                }
            }
            System.out.println(" Customer list loaded successfully from " + fname);
        } catch (IOException e) {
            System.out.println(" Error loading customers: " + e.getMessage());
        }
    }

    // ====== 2. Save list xuống file ======
    public void saveToFile(String fname) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fname))) {
            Node<Customer> p = head;
            while (p != null) {
                // Duyệt từng nút trong danh sách liên kết
                pw.println(p.info.ccode + " | " + p.info.cusName + " | " + p.info.phone);
                // Ghi thông tin khách hàng ra file theo định dạng bảng
                p = p.next;
            }
            System.out.println(" Saved to file: " + fname);
        } catch (IOException e) {
            System.out.println(" Error saving customers: " + e.getMessage());
        }
    }

    // ====== 3. Tìm khách hàng theo lựa chọn (ccode hoặc số điện thoại) ======
    public Customer searchByCcode(String code) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- SEARCH CUSTOMER ---");
        int choice = 0;
        while (true) {
            try {
                System.out.println("1. Search by customer code (ccode)");
                System.out.println("2. Search by phone number");
                System.out.print("Choose option (1-2): ");
                choice = Integer.parseInt(sc.nextLine().trim());
                if (choice == 1 || choice == 2) {
                    break;
                }
                System.out.println("⚠ Invalid choice! Please enter 1 or 2.\n");
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid input! Please enter a number (1 or 2).\n");
            }
        }
        // ===== 1️⃣ TÌM THEO MÃ KHÁCH HÀNG =====
        if (choice == 1) {
            String ccode;
            while (true) {
                System.out.print("Enter customer code: ");
                ccode = sc.nextLine().trim();
                if (ccode.isEmpty()) {
                    System.out.println("⚠ Customer code cannot be empty! Try again.");
                    continue;
                }
                break;
            }
            Node<Customer> p = head;
            while (p != null) {
                if (p.info.ccode.equalsIgnoreCase(ccode)) {
                    System.out.println("✅ Customer found: " + p.info);
                    return p.info;
                }
                p = p.next;
            }
            System.out.println("❌ Customer code '" + ccode + "' not found.");
            return null;
        } // ===== 2️⃣ TÌM THEO SỐ ĐIỆN THOẠI =====
        else {
            String phone;
            while (true) {
                System.out.print("Enter phone number: ");
                phone = sc.nextLine().trim();

                if (phone.isEmpty()) {
                    System.out.println("⚠ Phone number cannot be empty! Try again.");
                    continue;
                }
                if (!phone.matches("\\d+")) {
                    System.out.println("⚠ Phone number must contain digits only! Try again.");
                    continue;
                }
                break;
            }

            Node<Customer> p = head;
            while (p != null) {
                if (p.info.phone.equals(phone)) {
                    System.out.println("✅ Customer found: " + p.info);
                    return p.info;
                }
                p = p.next;
            }
            System.out.println("❌ Phone number '" + phone + "' not found.");
            return null;
        }
    }

    // ====== 4. Xóa khách hàng theo ccode (yêu cầu nhập lại khi lỗi) ======
    public void deleteByCcode(String code) {
        Scanner sc = new Scanner(System.in);
        String input = code;
        while (input == null || input.trim().isEmpty()) {
            System.out.print("Enter customer code to delete (or type '0' to cancel): ");
            input = sc.nextLine();
            if (input == null) {
                input = "";
            }
            input = input.trim();
            if (input.equals("0")) {
                System.out.println("Operation cancelled.");
                return;
            }
            if (input.isEmpty()) {
                System.out.println("⚠ Customer code is invalid! Please try again.");
            }
        }
        while (true) {
            String key = input.trim();
            if (isEmpty()) {
                System.out.println("⚠ Customer list is empty. Nothing to delete.");
                return;
            }
            if (head.info.ccode.equalsIgnoreCase(key)) {
                head = head.next;
                if (head == null) {
                    tail = null;
                }
                System.out.println("✅ Deleted customer " + key);
                return;
            }
            Node<Customer> p = head;
            boolean deleted = false;
            while (p.next != null) {
                if (p.next.info.ccode.equalsIgnoreCase(key)) {
                    // nếu node cần xóa là tail thì cập nhật tail
                    if (p.next == tail) {
                        tail = p;
                    }
                    // unlink node cần xóa
                    p.next = p.next.next;
                    System.out.println("✅ Deleted customer " + key);
                    deleted = true;
                    break;
                }
                p = p.next;
            }
            if (deleted) {
                return;
            }
            // nếu đến đây thì không tìm thấy mã
            System.out.println("⚠ Customer " + key + " not found.");
            // hỏi người dùng: thử lại hay hủy
            System.out.print("Do you want to try again? (Y/N): ");
            String ans = sc.nextLine();
            if (ans == null) {
                ans = "";
            }
            ans = ans.trim().toUpperCase();
            if (ans.equals("Y") || ans.equals("YES")) {
                // yêu cầu nhập mã lại; vòng while tiếp tục
                input = "";
                while (input.trim().isEmpty()) {
                    System.out.print("Enter customer code to delete (or type '0' to cancel): ");
                    input = sc.nextLine();
                    if (input == null) {
                        input = "";
                    }
                    input = input.trim();
                    if (input.equals("0")) {
                        System.out.println("Operation cancelled.");
                        return;
                    }
                    if (input.isEmpty()) {
                        System.out.println("⚠ Customer code is invalid! Please try again.");
                    }
                }
            } else {
                System.out.println("Operation cancelled.");
                return;
            }
        }
    }

    // ====== 5. Hiển thị danh sách ======
    public void display() {
        if (isEmpty()) {
            System.out.println(" Customer list is empty.");
            return;
        }
//    // In tiêu đề bảng
        System.out.println(String.format("%-5s | %-15s | %-10s", "Code", "Name", "Phone"));
        System.out.println("-------------------------------------------");

        Node<Customer> p = head;
        while (p != null) {
            System.out.println(p.info.toString());
            p = p.next;
        }
    }

    // ====== 6. Thêm khách hàng mới (bắt lỗi nhập lại từng bước) ======
    public void addCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Add New Customer ---");
        String code;
        while (true) {
            System.out.print("Enter customer code: ");
            code = sc.nextLine().trim();
            if (code.isEmpty()) {
                System.out.println("⚠ Code cannot be empty!");
                continue;
            }
            boolean exists = false;
            Node<Customer> temp = head;
            while (temp != null) {
                if (temp.info.ccode.equalsIgnoreCase(code)) {
                    exists = true;
                    break;
                }
                temp = temp.next;
            }
            if (exists) {
                System.out.println("⚠ Customer with code " + code + " already exists! Please enter again.");
            } else {
                break;
            }
        }
        String name;
        while (true) {
            System.out.print("Enter customer name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("⚠ Name cannot be empty!");
            } else {
                break;
            }
        }
        String phone;
        while (true) {
            System.out.print("Enter phone number: ");
            phone = sc.nextLine().trim();
            if (phone.isEmpty() || !phone.matches("\\d+")) {
                System.out.println("⚠ Invalid phone number! Must contain digits only.");
                continue;
            }
            boolean duplicatePhone = false;
            Node<Customer> p = head;
            while (p != null) {
                if (p.info.phone.equals(phone)) {
                    duplicatePhone = true;
                    System.out.println("⚠ Phone number already used by another customer!");
                    break;
                }
                p = p.next;
            }
            if (!duplicatePhone) {
                break;
            }
        }
        Customer newCustomer = new Customer(code, name, phone);
        addToTail(newCustomer);
        saveToFile("customer.txt");
        System.out.println("✅ Customer added successfully!");
    }

    // ====== 7. Kiểm tra danh sách rỗng ======
    boolean isEmpty() {
        return head == null; // true nếu head = null, tức danh sách rỗng
    }
    // ====== 8. Sắp xếp danh sách khách hàng theo ccode tăng dần ======

    public void sortCustomer() {
        if (isEmpty() || head.next == null) {
            // Danh sách rỗng hoặc chỉ 1 phần tử → không cần sắp xếp
            return;
        }

        // Dùng thuật toán sắp xếp nổi bọt (Bubble Sort) cho linked list
        boolean swapped;
        do {
            swapped = false;
            Node<Customer> current = head;
            Node<Customer> prev = null;

            while (current.next != null) {
                Node<Customer> nextNode = current.next;

                // So sánh ccode theo thứ tự chữ cái
                if (current.info.ccode.compareToIgnoreCase(nextNode.info.ccode) > 0) {
                    // Hoán đổi các nút
                    swapped = true;

                    if (prev == null) {
                        // current đang là head
                        head = nextNode;
                    } else {
                        prev.next = nextNode;
                    }

                    current.next = nextNode.next;
                    nextNode.next = current;

                    // Cập nhật prev sau khi hoán đổi
                    prev = nextNode;
                } else {
                    prev = current;
                    current = current.next;
                }
            }

        } while (swapped);

        System.out.println(" Customer list sorted by code successfully!");
    }

    public Customer findByCcode(String code) {
        Node<Customer> p = head;
        while (p != null) {
            if (p.info.ccode.equalsIgnoreCase(code)) {
                System.out.println("✅ Customer found: " + p.info);
                return p.info;
            }
            p = p.next;
        }
        System.out.println("❌ Customer code '" + code + "' not found.");
        return null;
    }

}
