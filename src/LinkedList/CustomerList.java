package LinkedList;

/*
 * ================================================================
 *  CLASS INFORMATION
 *  ================================================================
 *  Class name: CustomerList
 *  Author: Hoàng Thị Mỹ Duyên - HE187421
 *  Subject: CSD201 - Data Structures and Algorithms
 *  Project: Train Booking System (Assignment 1)
 *  ---------------------------------------------------------------
 *  Purpose:
 *      - Quản lý danh sách khách hàng (Customer Management)
 *      - Cấu trúc dữ liệu được sử dụng: Linked List (danh sách liên kết đơn)
 *      - Mỗi node chứa thông tin của 1 khách hàng (Customer)
 *      - Cho phép thực hiện các thao tác thêm, tìm kiếm, xóa, sắp xếp, hiển thị,
 *        lưu và tải dữ liệu khách hàng từ file.
 *
 *  ---------------------------------------------------------------
 *  Major Functionalities:
 *
 *  1. loadFromFile(String fname) 
 *      - Đọc dữ liệu khách hàng từ file văn bản (ví dụ: customer.txt)
 *      - Phân tách dữ liệu theo ký tự '|'
 *      - Thêm các khách hàng vào danh sách liên kết
 *      - Dùng khi khởi động chương trình để nạp dữ liệu cũ
 *
 *  2. saveToFile(String fname) 
 *      - Lưu toàn bộ danh sách khách hàng hiện tại xuống file
 *      - Mỗi khách hàng ghi trên một dòng theo định dạng chuẩn
 *      - Dùng khi thêm/xóa/cập nhật để đồng bộ dữ liệu
 *
 *  3. searchByCcode(String code) 
 *      - Cho phép tìm kiếm khách hàng theo mã (ccode) hoặc số điện thoại
 *      - Người dùng chọn cách tìm (menu nhỏ)
 *      - Hiển thị kết quả chi tiết của khách hàng nếu có
 *
 *  4. deleteByCcode(String code) 
 *      - Xóa khách hàng theo mã (ccode)
 *      - Có xác nhận và cho phép nhập lại nếu sai hoặc không tìm thấy
 *      - Tự động cập nhật head/tail nếu cần
 *
 *  5. display() 
 *      - Hiển thị danh sách khách hàng hiện tại ra màn hình
 *      - Dạng bảng có cột: Code | Name | Phone
 *      - Dùng trong menu “Display data”
 *
 *  6. addCustomer() 
 *      - Thêm khách hàng mới vào danh sách
 *      - Có kiểm tra trùng mã (ccode) và số điện thoại
 *      - Bắt lỗi người dùng (trống, không hợp lệ, trùng)
 *      - Sau khi thêm, tự động lưu lại file
 *
 *  7. isEmpty() 
 *      - Kiểm tra danh sách có rỗng hay không
 *      - Hỗ trợ các thao tác khác (ví dụ khi xóa hoặc hiển thị)
 *
 *  8. sortCustomer() 
 *      - Sắp xếp danh sách khách hàng theo mã (ccode) tăng dần
 *      - Thuật toán sử dụng: Bubble Sort cho Linked List
 *      - Dùng trong chức năng “Sort Customer List”
 *
 *  9. findByCcode(String code) 
 *      - Tìm nhanh khách hàng theo mã (ccode)
 *      - Trả về đối tượng Customer nếu tồn tại
 *
 *  10. updateCustomer() 
 *      - Cập nhật thông tin khách hàng hiện có
 *      - Cho phép giữ nguyên giá trị cũ nếu người dùng không nhập mới
 *      - Sau khi cập nhật, tự động ghi lại file
 */
import java.io.*;
import java.util.Scanner;

// CustomerList kế thừa MyLinkedList<Customer>, quản lý danh sách khách hàng
public class CustomerList extends MyLinkedList<Customer> {

    // ====== 1. Load dữ liệu từ file ======
    public void loadFromFile(String fname) {
        head = null;
        tail = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    String phone = parts[2].trim();
                    addToTail(new Customer(code, name, phone));
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
                pw.println(p.info.ccode + " | " + p.info.cusName + " | " + p.info.phone);
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
                System.out.println(" Invalid choice! Please enter 1 or 2.\n");
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input! Please enter a number (1 or 2).\n");
            }
        }
        // ===== 1️⃣ TÌM THEO MÃ KHÁCH HÀNG =====
        if (choice == 1) {
            String ccode;
            while (true) {
                System.out.print("Enter customer code: ");
                ccode = sc.nextLine().trim();
                if (ccode.isEmpty()) {
                    System.out.println(" Customer code cannot be empty! Try again.");
                    continue;
                }
                break;
            }
            Node<Customer> p = head;
            while (p != null) {
                if (p.info.ccode.equalsIgnoreCase(ccode)) {
                    System.out.println(" Customer found: " + p.info);
                    return p.info;
                }
                p = p.next;
            }
            System.out.println(" Customer code '" + ccode + "' not found.");
            return null;
        } // ===== 2️⃣ TÌM THEO SỐ ĐIỆN THOẠI =====
        else {
            String phone;
            while (true) {
                System.out.print("Enter phone number: ");
                phone = sc.nextLine().trim();

                if (phone.isEmpty()) {
                    System.out.println(" Phone number cannot be empty! Try again.");
                    continue;
                }
                if (!phone.matches("\\d+")) {
                    System.out.println(" Phone number must contain digits only! Try again.");
                    continue;
                }
                break;
            }

            Node<Customer> p = head;
            while (p != null) {
                if (p.info.phone.equals(phone)) {
                    System.out.println(" Customer found: " + p.info);
                    return p.info;
                }
                p = p.next;
            }
            System.out.println(" Phone number '" + phone + "' not found.");
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
                System.out.println(" Customer code is invalid! Please try again.");
            }
        }
        while (true) {
            String key = input.trim();
            if (isEmpty()) {
                System.out.println(" Customer list is empty. Nothing to delete.");
                return;
            }
            if (head.info.ccode.equalsIgnoreCase(key)) {
                head = head.next;
                if (head == null) {
                    tail = null;
                }
                System.out.println(" Deleted customer " + key);
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
                    System.out.println(" Deleted customer " + key);
                    deleted = true;
                    break;
                }
                p = p.next;
            }
            if (deleted) {
                return;
            }
            // nếu đến đây thì không tìm thấy mã
            System.out.println(" Customer " + key + " not found.");
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
                        System.out.println(" Customer code is invalid! Please try again.");
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
//        System.out.println("-------------------------------------------");

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

        // ====== Nhập mã khách hàng ======
        String code;
        while (true) {
            System.out.print("Enter customer code: ");
            code = sc.nextLine().trim();

            if (code.isEmpty()) {
                System.out.println(" Code cannot be empty!");
                continue;
            }

            // Kiểm tra không được là số âm (nếu nhập toàn số)
            if (code.matches("-\\d+")) {
                System.out.println(" Code cannot be negative!");
                continue;
            }

            // Kiểm tra trùng mã
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
                System.out.println(" Customer with code " + code + " already exists! Please enter again.");
            } else {
                break;
            }
        }

        // ====== Nhập tên khách hàng ======
        String name;
        while (true) {
            System.out.print("Enter customer name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println(" Name cannot be empty!");
            } else {
                break;
            }
        }

        // ====== Nhập số điện thoại ======
        String phone;
        while (true) {
            System.out.print("Enter phone number: ");
            phone = sc.nextLine().trim();

            // Không để trống, chỉ chứa số, không âm
            if (phone.isEmpty()) {
                System.out.println(" Phone number cannot be empty!");
                continue;
            }
            if (!phone.matches("\\d+")) {
                System.out.println(" Invalid phone number! Must contain digits only (no signs or letters).");
                continue;
            }
            if (phone.startsWith("0") && phone.length() == 1) {
                System.out.println(" Phone number is too short!");
                continue;
            }
            if (phone.startsWith("-")) {
                System.out.println(" Phone number cannot be negative!");
                continue;
            }

            // Kiểm tra trùng số điện thoại
            boolean duplicatePhone = false;
            Node<Customer> p = head;
            while (p != null) {
                if (p.info.phone.equals(phone)) {
                    duplicatePhone = true;
                    System.out.println(" Phone number already used by another customer!");
                    break;
                }
                p = p.next;
            }
            if (!duplicatePhone) {
                break;
            }
        }

        // ====== Tạo và thêm khách hàng ======
        Customer newCustomer = new Customer(code, name, phone);
        addToTail(newCustomer);
        saveToFile("customer.txt");
        System.out.println(" Customer added successfully!");
    }

    // ====== 7. Kiểm tra danh sách rỗng ======
    public boolean isEmpty() {
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

// ================== 9. TÌM KHÁCH HÀNG THEO MÃ ==================
    public Customer findByCcode(String code) {
        Node<Customer> p = head;
        while (p != null) {
            if (p.info.ccode.equalsIgnoreCase(code)) {
                System.out.println(" Customer found: " + p.info);
                return p.info;
            }
            p = p.next;
        }
        System.out.println(" Customer code '" + code + "' not found.");
        return null;
    }
// ================== 10. CẬP NHẬT THÔNG TIN KHÁCH HÀNG ==================

    public void updateCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- UPDATE CUSTOMER INFORMATION ---");

        // 1) Nhập mã khách hàng cần cập nhật (bắt nhập lại nếu để trống)
        String code;
        while (true) {
            System.out.print("Enter customer code to update: ");
            code = sc.nextLine().trim();
            if (code.isEmpty()) {
                System.out.println(" Customer code cannot be empty. Please enter again.");
                continue;
            }
            break;
        }

        // 2) Tìm customer theo mã (sử dụng hàm findByCcode hoặc searchByCodeOnly)
        Customer c = findByCcode(code); // nếu bạn đã đổi tên hàm, thay bằng hàm tìm phù hợp
        if (c == null) {
            // findByCcode in ra thông báo không tìm thấy rồi trả về null, nên chỉ return
            return;
        }

        // 3) Nhập tên mới (có thể để trống để giữ nguyên). Nếu nhập sai (chỉ toàn khoảng trắng) -> bắt nhập lại
        String newName;
        while (true) {
            System.out.print("Enter new name (press Enter to keep current '" + c.cusName + "'): ");
            newName = sc.nextLine();
            // Nếu người dùng chỉ nhập khoảng trắng -> coi là invalid, yêu cầu nhập lại
            if (newName != null) {
                newName = newName.trim();
            }
            if (newName == null) {
                newName = "";
            }

            if (newName.isEmpty()) {
                // Giữ nguyên tên cũ
                newName = c.cusName;
                break;
            }
            // Nếu nhập tên không rỗng -> chấp nhận (có thể thêm kiểm tra khác nếu cần)
            if (newName.length() == 0) {
                System.out.println(" Name cannot be blank. Please enter again.");
                continue;
            }
            break;
        }

        // 4) Nhập phone mới (có thể để trống để giữ nguyên).
        //    Nếu nhập không rỗng: phải là chữ số (\\d+), không trùng với số của khách khác.
        String newPhone;
        while (true) {
            System.out.print("Enter new phone (press Enter to keep current '" + c.phone + "'): ");
            newPhone = sc.nextLine();
            if (newPhone != null) {
                newPhone = newPhone.trim();
            }
            if (newPhone == null) {
                newPhone = "";
            }

            if (newPhone.isEmpty()) {
                // Giữ nguyên phone cũ
                newPhone = c.phone;
                break;
            }

            // Kiểm tra chỉ chứa chữ số
            if (!newPhone.matches("\\d+")) {
                System.out.println(" Invalid phone number! Must contain digits only. Please enter again.");
                continue;
            }

            // Kiểm tra không phải số âm (chỉ phòng trường hợp có dấu)
            if (newPhone.startsWith("-")) {
                System.out.println(" Phone number cannot be negative. Please enter again.");
                continue;
            }

            // Kiểm tra trùng số điện thoại với các customer khác (không tính chính khách hàng hiện tại)
            boolean duplicate = false;
            Node<Customer> p = head;
            while (p != null) {
                if (p.info != c && p.info.phone.equals(newPhone)) {
                    duplicate = true;
                    break;
                }
                p = p.next;
            }
            if (duplicate) {
                System.out.println(" Phone number already used by another customer. Please enter a different phone.");
                continue;
            }

            // Nếu qua hết kiểm tra -> chấp nhận
            break;
        }

        // 5) Gán lại thông tin và lưu file
        c.cusName = newName;
        c.phone = newPhone;

        saveToFile("customer.txt"); // lưu danh sách sau khi cập nhật
        System.out.println(" Customer updated successfully!");
    }

}
