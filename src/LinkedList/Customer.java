/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedList;

/*
 * ================================================================
 *  CLASS INFORMATION
 *  ================================================================
 *  Class name: Customer
 *  Author: Hoàng Thị Mỹ Duyên - HE187421
 *  Subject: CSD201 - Data Structures and Algorithms
 *  Project: Train Booking System (Assignment 1)
 *  ---------------------------------------------------------------
 *  Purpose:
 *      - Biểu diễn đối tượng "Customer" (Khách hàng) trong hệ thống Train Booking System.
 *      - Mỗi khách hàng gồm các thông tin cơ bản: mã khách hàng, tên, và số điện thoại.
 *      - Đây là lớp mô hình (model class), được dùng trong cấu trúc Linked List để
 *        quản lý danh sách khách hàng (CustomerList).
 *
 */

public class Customer {
    // ====== Thuộc tính ======
    String ccode;    // Mã khách hàng, là duy nhất (unique) dùng để nhận diện từng khách hàng
    String cusName;  // Tên khách hàng, lưu trữ thông tin tên đầy đủ
    String phone;    // Số điện thoại của khách hàng, chỉ chứa các chữ số

    // ====== Constructor ======
    public Customer(String ccode, String cusName, String phone) {
        // Constructor dùng để khởi tạo một đối tượng Customer mới
        // trim() loại bỏ khoảng trắng ở đầu và cuối chuỗi
        this.ccode = ccode.trim();      
        this.cusName = cusName.trim();
        this.phone = phone.trim();
    }

    // ====== Hàm hiển thị thông tin ======
    @Override
    public String toString() {
        // Ghi đè phương thức toString() để hiển thị khách hàng theo định dạng bảng
        // %-5s: căn trái, rộng 5 ký tự cho ccode
        // %-15s: căn trái, rộng 15 ký tự cho cusName
        // %-10s: căn trái, rộng 10 ký tự cho phone
        return String.format("%-5s | %-15s | %-10s", ccode, cusName, phone);
    }
}