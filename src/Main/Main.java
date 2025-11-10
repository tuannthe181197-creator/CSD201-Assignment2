package Main;

import java.util.Scanner;
import BST.TrainBST;
import BST.Train;
import LinkedList.CustomerList;
import Booking.BookingList;
import BST.TrainNode;
/**
 * Lớp chính của chương trình Quản lý Đặt vé Tàu (Train Booking System).
 * 
 * <p>Chương trình cho phép người dùng quản lý ba hệ thống chính:
 * <ul>
 *     <li>{@link BST.TrainBST} - Cây nhị phân tìm kiếm (BST) dùng để quản lý danh sách tàu.</li>
 *     <li>{@link LinkedList.CustomerList} - Danh sách liên kết dùng để quản lý khách hàng.</li>
 *     <li>{@link Booking.BookingList} - Danh sách quản lý các đặt vé giữa tàu và khách hàng.</li>
 * </ul>
 * 
 * <p>Các chức năng chính bao gồm:
 * <ul>
 *     <li>Quản lý tàu: tải, thêm, tìm kiếm, xóa, cân bằng cây, đếm số lượng...</li>
 *     <li>Quản lý khách hàng: tải, thêm, tìm kiếm, xóa, sắp xếp, cập nhật, lưu file...</li>
 *     <li>Quản lý đặt vé: thêm vé mới, hiển thị, sắp xếp, tải dữ liệu liên quan.</li>
 * </ul>
 * 
 * <p>Chương trình chạy trên môi trường console (giao diện văn bản).</p>
 * 
 *  // ========================= MODULE TÀU =========================
 * Hiển thị và xử lý menu quản lý tàu.
     * 
     * @param trains đối tượng {@link TrainBST} quản lý dữ liệu tàu
     * 
 * // ========================= MODULE KHÁCH HÀNG =========================
 * Hiển thị và xử lý menu quản lý khách hàng.
     * 
     * @param customers đối tượng {@link CustomerList} quản lý danh sách khách hàng
 *     // ========================= MODULE ĐẶT VÉ =========================
    * Hiển thị và xử lý menu quản lý đặt vé.
     * 
     * @param bookings  danh sách đặt vé {@link BookingList}
     * @param trains    cây nhị phân tàu {@link TrainBST}
     * @param customers danh sách khách hàng {@link CustomerList}
 * @author 
 * @version 1.0
 */

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        TrainBST trainBST = new TrainBST();
        CustomerList customerList = new CustomerList();
        BookingList bookingList = new BookingList();

        int choice;
        do {
            System.out.println("\n===== TRAIN BOOKING SYSTEM =====");
            System.out.println("1. Train System");
            System.out.println("2. Customer System");
            System.out.println("3. Booking System");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = getInt();

            switch (choice) {
                case 1:
                    trainMenu(trainBST);
                    break;
                case 2:
                    customerMenu(customerList);
                    break;
                case 3:
                    bookingMenu(bookingList, trainBST, customerList);
                    break;
                case 4:
                    System.out.println("Exit program...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    //================ TRAIN MODULE ================
    private static void trainMenu(TrainBST trains) {
        int c;
        do {
            System.out.println("\n--- TRAIN MENU ---");
            System.out.println("1. Load data from file trains.txt");
            System.out.println("2. Input & insert data");
            System.out.println("3. In-order traverse");
            System.out.println("4. Breadth-first traverse");
            System.out.println("5. In-order traverse to file");
            System.out.println("6. Search by tcode");
            System.out.println("7. Delete by tcode");
            System.out.println("8. Balance tree");
            System.out.println("9. Count number of trains");
            System.out.println("10. Back to main");
            System.out.print("Your choice: ");
            c = getInt();

            switch (c) {
                case 1:
                try {
                    int added = trains.loadFromFile("trains.txt");
                    System.out.println("Loaded from 'trains.txt'. Added: " + added);
                } catch (Exception e) {
                    System.out.println("Load failed: " + e.getMessage());
                }
                break;

                case 2: {
                    String tc = getUniqueTcode(trains, "tcode: ");
                    String name = getNonEmpty("name: ");
                    int seat = getIntWithMin("seat (>0): ", 1);
                    int booked = getIntRange("booked (0..seat): ", 0, seat);
                    double time = getDoubleMin("departTime (>=0): ", 0.0);
                    String place = getNonEmpty("departPlace: ");

                    try {
                        boolean ok = trains.insert(new Train(tc, name, seat, booked, time, place));
                        System.out.println(ok ? "Inserted." : "Duplicate tcode. Not inserted.");
                    } catch (Exception e) {
                        System.out.println("Insert failed: " + e.getMessage());
                    }
                    break;
                }
                case 3:
                    trains.inorder();
                    break;
                case 4:
                    trains.breadthFirst();
                    break;
                case 5: {
                    System.out.print("Output path (default: trains.txt): ");
                    String out = sc.nextLine().trim();
                    if (out.isEmpty()) {
                        out = "trains.txt";      // <==== fixed
                    }
                    try {
                        trains.saveInorderToFile(out);
                        System.out.println("Saved to '" + out + "'.");
                    } catch (Exception e) {
                        System.out.println("Save failed: " + e.getMessage());
                    }
                    break;
                }
                case 6: {
                    System.out.print("Enter tcodes (comma-separated): ");
                    String line = sc.nextLine().trim();

                    String[] codes = line.split(",");
                    for (String raw : codes) {
                        String key = raw.trim();
                        if (key.isEmpty()) {
                            continue;
                        }

                        TrainNode node = trains.search(key);
                        if (node == null) {
                            System.out.println(key + " -> Not found");
                        } else {
                            System.out.println(key + " -> " + node.info.toString());
                        }
                    }
                    break;
                }

                case 7: {
                    System.out.print("Enter tcode: ");
                    String key = sc.nextLine().trim();
                    boolean ok = trains.deleteByTcode(key);
                    System.out.println(ok ? "Deleted." : "Not found.");
                    break;
                }
                case 8:
                    trains.balance();
                    System.out.println("Balanced.");
                    break;
                case 9:
                    System.out.println("Total: " + trains.count());
                    break;
                case 10:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (c != 10);
    }

    // ========== CUSTOMER MODULE ==========
    private static void customerMenu(CustomerList customers) {
        int c;
        do {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Load from customer.txt");
            System.out.println("2. Add new customer");
            System.out.println("3. Display customers");
            System.out.println("4. Save to customer.txt");
            System.out.println("5. Search customer by ccode");
            System.out.println("6. Delete customer by ccode");
            System.out.println("7. Sort customers by ccode");
            System.out.println("8. Update customer information");
            System.out.println("9. Back to Main Menu");
            System.out.print("Your choice: ");
            c = getInt();

            switch (c) {
                case 1:
                    customers.loadFromFile("customer.txt");
                    break;
                case 2:
                    customers.addCustomer();
                    break;
                case 3:
                    if (customers.isEmpty()) {
                        System.out.println(" Customer list is empty.");
                    } else {
                        customers.display();
                    }
                    break;
                case 4:
                    customers.saveToFile("customer.txt");
                    break;
                case 5:
                    if (customers.isEmpty()) {
                        System.out.println(" Customer list is empty. Cannot search.");
                    } else {
                        System.out.print("Enter ccode: ");
                        customers.findByCcode(sc.nextLine());
                    }
                    break;
                case 6:
                    if (customers.isEmpty()) {
                        System.out.println(" Customer list is empty. Nothing to delete.");
                    } else {
                        System.out.print("Enter ccode: ");
                        customers.deleteByCcode(sc.nextLine());
                    }
                    break;
                case 7:
                    if (customers.isEmpty()) {
                        System.out.println(" Customer list is empty. Cannot sort.");
                    } else {
                        customers.sortCustomer();
                    }
                    break;
                case 8:
                    if (customers.isEmpty()) {
                        System.out.println(" Customer list is empty. Nothing to update.");
                    } else {
                        customers.updateCustomer();
                    }
                    break;
                case 9:
                    System.out.println("Return to Main Menu...");
                    break;
                default:
                    System.out.println(" Invalid choice! Please try again.");
            }

        } while (c != 9);

        switch (c) {
            case 1:
                customers.loadFromFile("customer.txt");
                break;
            case 2:
                customers.addCustomer();
                break;
            case 3:
                if (customers.isEmpty()) {
                    System.out.println(" Customer list is empty.");
                } else {
                    customers.display();
                }
                break;
            case 4:
                customers.saveToFile("customer.txt");
                break;
            case 5:
                if (customers.isEmpty()) {
                    System.out.println(" Customer list is empty. Cannot search.");
                } else {
                    System.out.print("Enter ccode: ");
                    customers.findByCcode(sc.nextLine());
                }
                break;
            case 6:
                if (customers.isEmpty()) {
                    System.out.println(" Customer list is empty. Nothing to delete.");
                } else {
                    System.out.print("Enter ccode: ");
                    customers.deleteByCcode(sc.nextLine());
                }
                break;
            case 7:
                if (customers.isEmpty()) {
                    System.out.println(" Customer list is empty. Cannot sort.");
                } else {
                    customers.sortCustomer();
                }
                break;
            case 8:
                if (customers.isEmpty()) {
                    System.out.println(" Customer list is empty. Nothing to update.");
                } else {
                    customers.updateCustomer();
                }
                break;
            case 9:
                System.out.println("Return to Main Menu...");
                break;
            default:
                System.out.println(" Invalid choice! Please try again.");
        }

    }

    //================ BOOKING MODULE ================
    private static void bookingMenu(BookingList bookings, TrainBST trains, CustomerList customers) {
        int c;
        do {
            System.out.println("\n--- BOOKING MENU ---");
            System.out.println("1. Load train data");
            System.out.println("2. Load customer data");
            System.out.println("3. Input booking");
            System.out.println("4. Display bookings");
            System.out.println("5. Sort booking");
            System.out.println("6. Back to main");
            System.out.print("Your choice: ");
            c = getInt();

            switch (c) {
                case 1:
                    bookings.loadTrainData(trains, "trains.txt");
                    break;
                case 2:
                    bookings.loadCustomerData(customers, "customer.txt");
                    break;
                case 3:
                    // validate phải load trước
                    if (trains.isEmpty()) {
                        System.out.println(" Train data not loaded!");
                        break;
                    }
                    if (customers.isEmpty()) {
                        System.out.println(" Customer data not loaded!");
                        break;
                    }
                    bookings.inputBooking(trains, customers);
                    break;
                case 4:
                    bookings.displayBookings();
                    break;
                case 5:
                    bookings.sortByTcodeAndCcode();
                    break;
            }

        } while (c != 6);
    }

    //============= UTILITY ============
    // ========================= HÀM HỖ TRỢ =========================

    /** 
     * Đọc một số nguyên từ bàn phím, lặp lại đến khi hợp lệ.  
     * @return giá trị số nguyên do người dùng nhập
     */
    private static int getInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Invalid! Enter number again: ");
            }
        }
    }

     /** 
     * Đọc số nguyên có giá trị tối thiểu.
     * @param msg thông báo hiển thị
     * @param min giá trị nhỏ nhất cho phép
     * @return số nguyên hợp lệ
     */
    private static int getIntWithMin(String msg, int min) {
        while (true) {
            System.out.print(msg);
            int v = getInt();
            if (v >= min) {
                return v;
            }
            System.out.println("Value must be >= " + min);
        }
    }

    
    /** 
     * Đọc số nguyên trong khoảng cho phép.
     * @param msg thông báo hiển thị
     * @param min giá trị nhỏ nhất
     * @param max giá trị lớn nhất
     * @return số nguyên hợp lệ
     */
    private static int getIntRange(String msg, int min, int max) {
        while (true) {
            System.out.print(msg);
            int v = getInt();
            if (v < min || v > max) {
                System.out.println("Value must be between " + min + " and " + max);
            } else {
                return v;
            }
        }
    }

     /** 
     * Đọc số thực từ người dùng.
     * @return giá trị double hợp lệ
     */
    private static double getDouble() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.print("Invalid! Enter number again: ");
            }
        }
    }
    
/** 
     * Đọc số thực ≥ giá trị tối thiểu.
     * @param msg thông báo hiển thị
     * @param min giá trị nhỏ nhất
     * @return số double hợp lệ
     */
    private static double getDoubleMin(String msg, double min) {
        while (true) {
            System.out.print(msg);
            double v = getDouble();
            if (v >= min) {
                return v;
            }
            System.out.println("Value must be >= " + min);
        }
    }

     /** 
     * Đọc chuỗi không rỗng.
     * @param msg thông báo hiển thị
     * @return chuỗi không rỗng, đã loại bỏ khoảng trắng thừa
     */
    private static String getNonEmpty(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine();
            if (s != null && !s.trim().isEmpty()) {
                return s.trim();
            }
            System.out.println("Value cannot be empty.");
        }
    }

    /** 
     * Nhập mã tàu (tcode) duy nhất chưa tồn tại trong cây.
     * @param trains cây tàu để kiểm tra trùng lặp
     * @param prompt thông báo nhập liệu
     * @return mã tàu hợp lệ và duy nhất
     */
    private static String getUniqueTcode(TrainBST trains, String prompt) {
        while (true) {
            String tcode = getNonEmpty(prompt);
            try {
                Object existed = trains.search(tcode); // có thể là Train hoặc Node tùy cài đặt
                if (existed == null) {
                    return tcode;
                }
                System.out.println("tcode '" + tcode + "' already exists. Please re-enter.");
            } catch (NoSuchMethodError err) {

                return tcode; // vẫn trả về, insert sẽ bắt trùng phía sau
            } catch (Exception e) {
                System.out.println("Cannot verify tcode: " + e.getMessage() + " → please re-enter.");
            }
        }
    }
}
