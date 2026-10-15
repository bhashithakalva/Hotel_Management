package payments;

import java.time.LocalDate;

public class RefundRecord {
    private String refundId;
    private String bookingId;
    private double refundAmount;
    private String refundStatus;
    private LocalDate refundDate;

    public RefundRecord(String refundId, String bookingId, double refundAmount) {
        this(refundId, bookingId, refundAmount, "PENDING");
    }
    public RefundRecord(String refundId, String bookingId, double refundAmount, String refundStatus) {
        this.refundId = refundId;
        this.bookingId = bookingId;
        this.refundAmount = refundAmount;
        this.refundStatus = refundStatus;
        this.refundDate = LocalDate.now();
    }
    public String getRefundId() { return refundId; }
    public String getBookingId() { return bookingId; }
    public double getRefundAmount() { return refundAmount; }
    public String getRefundStatus() { return refundStatus; }
    public LocalDate getRefundDate() { return refundDate; }
    public void setRefundStatus(String refundStatus) { this.refundStatus = refundStatus; }
    public RefundRecord(String refundId, String bookingId, double refundAmount,
                        String refundStatus, LocalDate refundDate) {
        this.refundId = refundId;
        this.bookingId = bookingId;
        this.refundAmount = refundAmount;
        this.refundStatus = refundStatus;
        this.refundDate = refundDate;
    }

    public String toRecord() {
        return refundId + "," + bookingId + "," + refundAmount + "," + refundStatus + "," + refundDate;
    }
    public void displayRefund() {
        System.out.println(refundId + " | Booking: " + bookingId + " | Refund: Rs. " + refundAmount + " | " + refundStatus + " | " + refundDate);
    }
}
