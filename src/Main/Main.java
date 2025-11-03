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
                case 1:
                    trains.loadFromFile("train.txt");
                    break;
                case 2:
                    // main tự nhập Train
                    System.out.print("tcode: ");
                    String tc = sc.nextLine();
                    System.out.print("name: ");
                    String name = sc.nextLine();
                    System.out.print("seat: ");
                    int seat = getInt();
                    System.out.print("booked: ");
                    int booked = getInt();
                    System.out.print("departTime: ");
                    double time = Double.parseDouble(sc.nextLine());
                    System.out.print("departPlace: ");
                    String place = sc.nextLine();
                    trains.insert(new Train(tc, name, seat, booked, time, place));
                    break;
                case 3:
                    trains.inorder();
                    break;
                case 4:
                    trains.breadthFirst();
                    break;
                case 5:
                    trains.saveInorderToFile("train.txt");
                    break;
                case 6:
                    System.out.print("Enter tcode: ");
                    trains.search(sc.nextLine());
                    break;
                case 7:
                    System.out.print("Enter tcode: ");
                    trains.deleteByTcode(sc.nextLine());
                    break;
                case 8:
                    trains.balance();
                    break;
                case 9:
                    System.out.println("Total: " + trains.count());
                    break;
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
}
