package roomsystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {

    private String bookingId;
    private String customerId;
    private int roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalAmount;
    private double advanceAmount;
    private String status;

    public Reservation(String bookingId, String customerId, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate, double pricePerNight, double advanceAmount) {

        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

        this.totalAmount = ChronoUnit.DAYS.between(checkInDate, checkOutDate) * pricePerNight;

        this.advanceAmount = advanceAmount;
        this.status = "CONFIRMED";
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getAdvanceAmount() {
        return advanceAmount;
    }

    public double getRemainingAmount() {
        return totalAmount - advanceAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toRecord() {
        return bookingId + "," + customerId + "," + roomNumber + "," + checkInDate + "," + checkOutDate + "," + totalAmount + "," + advanceAmount + "," + status;
    }

    public static Reservation fromRecord(String record) {
        String[] parts = record.split(",");

        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid reservation record");
        }

        String bookingId = parts[0];
        String customerId = parts[1];
        int roomNumber = Integer.parseInt(parts[2]);
        LocalDate checkInDate = LocalDate.parse(parts[3]);
        LocalDate checkOutDate = LocalDate.parse(parts[4]);
        double totalAmount = Double.parseDouble(parts[5]);
        double advanceAmount = Double.parseDouble(parts[6]);
        String status = parts[7];

        Reservation reservation = new Reservation(  bookingId,  customerId,  roomNumber,  checkInDate,  checkOutDate,  checkOutDate.isAfter(checkInDate) ? totalAmount / ChronoUnit.DAYS.between(checkInDate, checkOutDate) : 0,  advanceAmount);

        reservation.setStatus(status);

        return reservation;
    }

    public void displayReservation() {
        System.out.println("========== BOOKING DETAILS ==========");
        System.out.println("Booking ID       : " + bookingId);
        System.out.println("Customer ID      : " + customerId);
        System.out.println("Room Number      : " + roomNumber);
        System.out.println("Check-in Date    : " + checkInDate);
        System.out.println("Check-out Date   : " + checkOutDate);
        System.out.println("Total Amount     : Rs. " + totalAmount);
        System.out.println("Advance Amount   : Rs. " + advanceAmount);
        System.out.println("Remaining Amount : Rs. " + getRemainingAmount());
        System.out.println("Booking Status   : " + status);
        System.out.println("=====================================");
    }
}