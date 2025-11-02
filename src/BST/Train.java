package BST;

public class Train {
    private String tcode;
    private String name;
    private int seat;
    private int booked;
    private double departTime;
    private String departPlace;

    public Train(String tcode, String name, int seat, int booked, double departTime, String departPlace) {
        this.tcode = tcode.trim();
        this.name = name.trim();
        this.seat = seat;
        this.booked = booked;
        this.departTime = departTime;
        this.departPlace = departPlace.trim();
        validate();
    }

    public static Train parse(String line) {
        if (line == null) return null;
        String[] p = line.split("\\|");
        if (p.length < 6) return null;
        String tcode = p[0].trim();
        String name = p[1].trim();
        int seat = Integer.parseInt(p[2].trim());
        int booked = Integer.parseInt(p[3].trim());
        double departTime = Double.parseDouble(p[4].trim());
        String departPlace = p[5].trim();
        return new Train(tcode, name, seat, booked, departTime, departPlace);
    }

    public void validate() throws IllegalArgumentException {
        if (tcode == null || tcode.isEmpty()) throw new IllegalArgumentException("tcode is required");
        if (seat <= 0) throw new IllegalArgumentException("seat must be > 0");
        if (booked < 0 || booked > seat) throw new IllegalArgumentException("booked must be between 0 and seat");
        if (departTime < 0) throw new IllegalArgumentException("departTime must be >= 0");
    }

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
