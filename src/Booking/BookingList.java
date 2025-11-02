/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

/**
 *
 * @author Dell
 */
import java.util.*;

public class BookingList {

    LinkedList<Booking> list = new LinkedList<>();

    // 3.1 Input data
    public void inputBooking() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter train code (tcode): ");
        String tcode = sc.nextLine();
        System.out.print("Enter customer code (ccode): ");
        String ccode = sc.nextLine();
        System.out.print("Enter number of seats: ");
        int seat = Integer.parseInt(sc.nextLine());

        list.add(new Booking(tcode, ccode, seat));
        System.out.println("✅ Booking added successfully!\n");
    }

    // 3.2 Display booking data
    public void displayBookings() {
        if (list.isEmpty()) {
            System.out.println("⚠️ No booking data available.");
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
        System.out.println("✅ Sorted booking list by tcode + ccode.\n");
    }
}
