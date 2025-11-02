/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

/**
 *
 * @author Dell
 */
public class Booking {

    String tcode;
    String ccode;
    int seat;

    public Booking(String tcode, String ccode, int seat) {
        this.tcode = tcode;
        this.ccode = ccode;
        this.seat = seat;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-10s | %5d", tcode, ccode, seat);
    }
}
