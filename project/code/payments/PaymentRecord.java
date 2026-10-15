package payments;

import java.time.LocalDate;

public class PaymentRecord {

    private String paymentId;
    private String bookingId;
    private double amount;
    private String paymentType;
    private String paymentStatus;
    private LocalDate paymentDate;

    public PaymentRecord(String paymentId, String bookingId, double amount, String paymentType) {

        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentStatus = "SUCCESSFUL";
        this.paymentDate = LocalDate.now();
    }

    public PaymentRecord(String paymentId, String bookingId,
                         double amount, String paymentType,
                         String paymentStatus) {

        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.paymentDate = LocalDate.now();
    }

    public String toRecord() {
        return paymentId + "," +
                bookingId + "," +
                amount + "," +
                paymentType + "," +
                paymentStatus + "," +
                paymentDate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void displayPayment() {
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Amount: Rs. " + amount);
        System.out.println("Payment Type: " + paymentType);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Payment Date: " + paymentDate);
    }
}