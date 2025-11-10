/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

/**
 *
 * @author Dell
 */
import BST.TrainBST;
import BST.TrainNode;
import BST.Train;
import LinkedList.CustomerList;
import LinkedList.Customer;

import java.util.*;

public class BookingList {

    LinkedList<Booking> list = new LinkedList<>();

    public boolean loadTrainData(TrainBST trains, String file) {
        int n = trains.loadFromFile(file);
        return n > 0;
    }

    public boolean loadCustomerData(CustomerList customers, String file) {
        customers.loadFromFile(file);
        return !customers.isEmpty();
    }

    // 3.1 Input data
    public void inputBooking(TrainBST trains, CustomerList customers) {
        Scanner sc = new Scanner(System.in);

        // nhập tcode
        String tcode;
        while (true) {
            System.out.print("Enter train code (tcode): ");
            tcode = sc.nextLine().trim();
            TrainNode node = trains.search(tcode);
            if (node == null) {
                System.out.println(" Train not found!");
            } else {
                break;
            }
        }

        // nhập ccode
        String ccode;
        while (true) {
            System.out.print("Enter customer code (ccode): ");
            ccode = sc.nextLine().trim();
            Customer c = customers.findByCcode(ccode);
            if (c == null) {
                System.out.println(" Customer not found!");
            } else {
                break;
            }
        }

        // nhập số ghế
        int seat;
        while (true) {
            System.out.print("Enter number of seats: ");
            try {
                seat = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(" Must be a number!");
                continue;
            }
            if (seat <= 0) {
                System.out.println(" Seat must be > 0!");
                continue;
            }

            TrainNode tnode = trains.search(tcode);
            int available = tnode.info.available();
            if (seat > available) {
                System.out.println(" Not enough seats! Available: " + available);
                continue;
            }
            break;
        }

        // OK → add Booking
        list.add(new Booking(tcode, ccode, seat));

        // update booked trên train
        Train train = trains.search(tcode).info;
        train.setBooked(train.getBooked() + seat);

        System.out.println(" Booking added successfully!\n");
    }

    // 3.2 Display booking data
    public void displayBookings() {
        if (list.isEmpty()) {
            System.out.println(" No booking data available.");
            return;
        }

        System.out.println("\n====== Booking List ======");
        System.out.printf("%-10s | %-10s | %-5s\n", "Train", "Customer", "Seat");
        System.out.println("----------------------------");
        for (Booking b : list) {
            System.out.println(b);
        }
        System.out.println();
    }

    // 3.3 Sort by tcode + ccode
    public void sortByTcodeAndCcode() {
        Collections.sort(list, new Comparator<Booking>() {
            @Override
            public int compare(Booking b1, Booking b2) {
                int cmp = b1.tcode.compareToIgnoreCase(b2.tcode);
                if (cmp != 0) {
                    return cmp;
                }
                return b1.ccode.compareToIgnoreCase(b2.ccode);
            }
        });
        System.out.println(" Sorted booking list by tcode + ccode.\n");
    }
}
