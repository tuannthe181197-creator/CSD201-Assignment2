package BST;
/*
=====================================================================
                          CLASS INFORMATION
=====================================================================

Class name: Train
Author: Phạm Phúc Thiện – HE190564
Subject: CSD201 – Data Structures and Algorithms
Project: Train Booking System (Assignment 1)

---------------------------------------------------------------------
Purpose:
- Đại diện cho một chuyến tàu (Train record) trong hệ thống.
- Lưu trữ các thuộc tính: tcode, name, seat, booked, departTime, departPlace.
- Cung cấp kiểm tra ràng buộc dữ liệu (validate) để đảm bảo hợp lệ.
- Hỗ trợ parse từ 1 dòng dữ liệu định dạng pipe (tcode|name|seat|booked|depart_time|place).
- Hỗ trợ xuất ra 1 dòng dữ liệu để lưu file (toDataLine) và hiển thị (toString).

---------------------------------------------------------------------
Major Functionalities:
1. parse(String line)
   * Đọc 1 dòng văn bản theo định dạng: tcode|name|seat|booked|depart_time|place.
   * Tách và chuyển kiểu dữ liệu; ném IllegalArgumentException khi sai định dạng.

2. validate()
   * Kiểm tra các ràng buộc nghiệp vụ:
     - tcode/name/departPlace không rỗng
     - seat > 0
     - 0 <= booked <= seat
     - departTime >= 0
   * Ném IllegalArgumentException với thông báo rõ ràng khi vi phạm.

3. toDataLine()
   * Xuất đối tượng về chuỗi pipe “tcode|name|seat|booked|departTime|departPlace”
     để phục vụ lưu file.

4. toString()
   * Trả về chuỗi format đẹp để in bảng trên console.

5. available(), isFull()
   * Tính số ghế còn lại (seat - booked) và kiểm tra tàu đã đầy hay chưa.

6. Getter/Setter cần thiết
   * Cung cấp các phương thức truy cập và cập nhật hợp lệ cho thuộc tính.
=====================================================================
*/
public class Train {
    private String tcode;
    private String name;
    private int seat;
    private int booked;
    private double departTime;
    private String departPlace;

    public Train(String tcode, String name, int seat, int booked, double departTime, String departPlace) {
        this.tcode = safeTrim(tcode);
        this.name = safeTrim(name);
        this.seat = seat;
        this.booked = booked;
        this.departTime = departTime;
        this.departPlace = safeTrim(departPlace);
        validate();
    }

    /** Parse 1 dòng theo định dạng: tcode|name|seat|booked|depart_time|place (ném IllegalArgumentException khi lỗi) */
    public static Train parse(String line) {
        if (line == null) throw new IllegalArgumentException("Empty line");
        String[] p = line.split("\\|");
        if (p.length < 6) throw new IllegalArgumentException("Invalid format, need 6 fields: " + line);

        String tcode = safeTrim(p[0]);
        String name = safeTrim(p[1]);
        int seat;
        int booked;
        double departTime;
        String departPlace = safeTrim(p[5]);

        try {
            seat = Integer.parseInt(p[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Seat must be an integer: " + p[2]);
        }
        try {
            booked = Integer.parseInt(p[3].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Booked must be an integer: " + p[3]);
        }
        try {
            departTime = Double.parseDouble(p[4].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Depart_time must be a number: " + p[4]);
        }
        return new Train(tcode, name, seat, booked, departTime, departPlace);
    }

    /** Kiểm tra ràng buộc nghiệp vụ, ném IllegalArgumentException với message rõ ràng */
    public void validate() throws IllegalArgumentException {
        if (tcode == null || tcode.isEmpty()) throw new IllegalArgumentException("tcode is required");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("train_name is required");
        if (seat <= 0) throw new IllegalArgumentException("seat must be > 0");
        if (booked < 0 || booked > seat) throw new IllegalArgumentException("booked must be between 0 and seat");
        if (departTime < 0) throw new IllegalArgumentException("depart_time must be >= 0");
        if (departPlace == null || departPlace.isEmpty()) throw new IllegalArgumentException("depart_place is required");
    }

    private static String safeTrim(String s){ return s == null ? "" : s.trim(); }

    public String getTcode() { return tcode; }
    public String getName() { return name; }
    public int getSeat() { return seat; }
    public int getBooked() { return booked; }
    public double getDepartTime() { return departTime; }
    public String getDepartPlace() { return departPlace; }

    public void setBooked(int booked) {
        this.booked = booked;
        validate();
    }

    public int available() { return seat - booked; }
    public boolean isFull() { return available() <= 0; }

    public String toDataLine() {
        return tcode + "|" + name + "|" + seat + "|" + booked + "|" + departTime + "|" + departPlace;
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-18s | %5d | %5d | %6.2f | %-12s",
                tcode, name, seat, booked, departTime, departPlace);
    }
}
