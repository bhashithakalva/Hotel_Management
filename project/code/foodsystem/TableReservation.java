package foodsystem;

import java.time.LocalDate;

public class TableReservation {

    private String reservationId;
    private String customerId;
    private int tableNumber;
    private LocalDate reservationDate;
    private int numberOfPeople;
    private String status;

    public TableReservation(String reservationId, String customerId,int tableNumber, LocalDate reservationDate,int numberOfPeople) {

        this.reservationId = reservationId;
        this.customerId = customerId;
        this.tableNumber = tableNumber;
        this.reservationDate = reservationDate;
        this.numberOfPeople = numberOfPeople;
        this.status = "CONFIRMED";
    }

    public String toRecord() {
        return reservationId + "," + customerId + "," + tableNumber + "," + reservationDate + "," + numberOfPeople + "," + status;
    }
    
    public String getReservationId() {
        return reservationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Table Number: " + tableNumber);
        System.out.println("Reservation Date: " + reservationDate);
        System.out.println("Number of People: " + numberOfPeople);
        System.out.println("Status: " + status);
    }
}