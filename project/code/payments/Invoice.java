package payments;

import contracts.Payable;

public class Invoice implements Payable {

    private String invoiceId;
    private String bookingId;
    private double roomCharges;
    private double foodCharges;
    private double serviceCharges;
    private double amountPaid;

    public Invoice(String invoiceId, String bookingId,
                   double roomCharges, double foodCharges,
                   double serviceCharges, double amountPaid) {
        this.invoiceId = invoiceId;
        this.bookingId = bookingId;
        this.roomCharges = roomCharges;
        this.foodCharges = foodCharges;
        this.serviceCharges = serviceCharges;
        this.amountPaid = amountPaid;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    @Override
    public double calculateAmount() {
        return roomCharges + foodCharges + serviceCharges;
    }

    public double calculateRemainingAmount() {
        return Math.max(0, calculateAmount() - amountPaid);
    }

    public void displayInvoice() {
        System.out.println("========== HOTEL INVOICE ==========");
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Room Charges: Rs. " + roomCharges);
        System.out.println("Food Charges: Rs. " + foodCharges);
        System.out.println("Service Charges: Rs. " + serviceCharges);
        System.out.println("Total Bill: Rs. " + calculateAmount());
        System.out.println("Total Paid: Rs. " + amountPaid);
        System.out.println("Remaining Amount: Rs. " + calculateRemainingAmount());
        System.out.println("===================================");
    }
}
