package Main;

import java.util.Scanner;
import BST.TrainBST;
import BST.Train;
import LinkedList.CustomerList;
import Booking.BookingList;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        TrainBST trainBST = new TrainBST();
        CustomerList customerList = new CustomerList();
        BookingList bookingList = new BookingList();

        int choice;
        do {
            System.out.println("\n===== TRAIN BOOKING SYSTEM =====");
            System.out.println("1. Train Module");
            System.out.println("2. Customer Module");
            System.out.println("3. Booking Module");
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
                    bookingMenu(bookingList);
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
            System.out.println("1. Load data from file train.txt");
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
                case 1: {
                    System.out.print("Enter path (default: trains.txt): ");
                    String path = sc.nextLine().trim();
                    if (path.isEmpty()) path = "trains.txt";
                    try {
                        int added = trains.loadFromFile(path);
                        System.out.println("Loaded from '" + path + "'. Added: " + added);
                    } catch (Exception e) {
                        System.out.println("Load failed: " + e.getMessage());
                    }
                    break;
                }
                case 2: {
                    // NHẬP LẠI TCODE NẾU TRÙNG
                    String tc = getUniqueTcode(trains, "tcode: ");
                    String name = getNonEmpty("name: ");
                    int seat = getIntWithMin("seat (>0): ", 1);
                    int booked = getIntRange("booked (0..seat): ", 0, seat);
                    double time = getDoubleMin("departTime (>=0): ", 0.0);
                    String place = getNonEmpty("departPlace: ");

                    try {
                        boolean ok = trains.insert(new Train(tc, name, seat, booked, time, place));
                        System.out.println(ok ? "Inserted." : "Duplicate tcode. Not inserted.");
                    } catch (IllegalArgumentException iae) {
                        System.out.println("Invalid input: " + iae.getMessage());
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
                    System.out.print("Output path (default: train.txt): ");
                    String out = sc.nextLine().trim();
                    if (out.isEmpty()) out = "train.txt";
                    try {
                        trains.saveInorderToFile(out);
                        System.out.println("Saved to '" + out + "'.");
                    } catch (Exception e) {
                        System.out.println("Save failed: " + e.getMessage());
                    }
                    break;
                }
                case 6: {
                    System.out.print("Enter tcode: ");
                    String key = sc.nextLine().trim();
                    try {
                        Object found = trains.search(key);
                        if (found == null) {
                            System.out.println("Not found.");
                        } else {
                            System.out.println("Found: " + found);
                        }
                    } catch (Exception e) {
                        System.out.println("Search failed: " + e.getMessage());
                    }
                    break;
                }
                case 7: {
                    System.out.print("Enter tcode: ");
                    String key = sc.nextLine().trim();
                    try {
                        boolean ok = trains.deleteByTcode(key);
                        System.out.println(ok ? "Deleted." : "Not found.");
                    } catch (Exception e) {
                        System.out.println("Delete failed: " + e.getMessage());
                    }
                    break;
                }
                case 8:
                    try {
                        trains.balance();
                        System.out.println("Balanced.");
                    } catch (Exception e) {
                        System.out.println("Balance failed: " + e.getMessage());
                    }
                    break;
                case 9:
                    System.out.println("Total: " + trains.count());
                    break;
                case 10:
                    // back
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (c != 10);
    }


    //================ CUSTOMER MODULE ================
    private static void customerMenu(CustomerList customers) {
        int c;
        do {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Load from file customer.txt");
            System.out.println("2. Input & add to end");
            System.out.println("3. Display");
            System.out.println("4. Save to file customer.txt");
            System.out.println("5. Search by ccode");
            System.out.println("6. Delete by ccode");
            System.out.println("7. Sort Customer by ccode");
            System.out.println("8. Update Info Customer");
            System.out.println("9. Back to main");
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
                    customers.traverse();
                    break;
                case 4:
                    customers.saveToFile("customer.txt");
                    break;
                case 5:
                    System.out.print("Enter ccode: ");
                    customers.findByCcode(sc.nextLine());
                    break;
                case 6:
                    System.out.print("Enter ccode: ");
                    customers.deleteByCcode(sc.nextLine());
                    break;
                    
            }

        } while (c != 7);
    }

    //================ BOOKING MODULE ================
    private static void bookingMenu(BookingList bookings) {
        int c;
        do {
            System.out.println("\n--- BOOKING MENU ---");
            System.out.println("1. Input booking");
            System.out.println("2. Display bookings");
            System.out.println("3. Sort by tcode + ccode");
            System.out.println("4. Back to main");
            System.out.print("Your choice: ");
            c = getInt();

            switch (c) {
                case 1:
                    bookings.inputBooking();
                    break;
                case 2:
                    bookings.displayBookings();
                    break;
                case 3:
                    bookings.sortByTcodeAndCcode();
                    break;
            }

        } while (c != 4);
    }

    //============= UTILITY ============
    private static int getInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Invalid! Enter number again: ");
            }
        }
    }
    private static int getIntWithMin(String msg, int min) {
        while (true) {
            System.out.print(msg);
            int v = getInt();
            if (v >= min) return v;
            System.out.println("Value must be >= " + min);
        }
    }

    private static int getIntRange(String msg, int min, int max) {
        while (true) {
            System.out.print(msg);
            int v = getInt();
            if (v < min || v > max) {
                System.out.println("Value must be between " + min + " and " + max);
            } else return v;
        }
    }

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

    private static double getDoubleMin(String msg, double min) {
        while (true) {
            System.out.print(msg);
            double v = getDouble();
            if (v >= min) return v;
            System.out.println("Value must be >= " + min);
        }
    }

    private static String getNonEmpty(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("Value cannot be empty.");
        }
    }
    private static String getUniqueTcode(TrainBST trains, String prompt) {
        while (true) {
            String tcode = getNonEmpty(prompt);
            try {
                Object existed = trains.search(tcode); // có thể là Train hoặc Node tùy cài đặt
                if (existed == null) return tcode;
                System.out.println("tcode '" + tcode + "' already exists. Please re-enter.");
            } catch (NoSuchMethodError err) {
               
                return tcode; // vẫn trả về, insert sẽ bắt trùng phía sau
            } catch (Exception e) {
                System.out.println("Cannot verify tcode: " + e.getMessage() + " → please re-enter.");
            }
        }
    }
}
