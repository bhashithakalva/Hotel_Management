package operations;

import java.util.List;
import roomsystem.RoomDetails;
import roomsystem.Reservation;
import payments.Invoice;
import storage.FileStorage;

public class CheckOutDesk {

    private ReservationDesk reservationDesk;
    private FileStorage storage;

    public CheckOutDesk(ReservationDesk reservationDesk) {
        this.reservationDesk = reservationDesk;
    }

    public CheckOutDesk(ReservationDesk reservationDesk,FileStorage storage) {
        this.reservationDesk = reservationDesk;
        this.storage = storage;
    }

    public void checkOut(String bookingId) {

        Reservation reservation = reservationDesk.findReservation(bookingId);

        if (reservation == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (!reservation.getStatus().equalsIgnoreCase("CHECKED_IN")) {
            System.out.println("Guest has not checked in.");
            return;
        }

        RoomDetails room = reservationDesk.findRoom(reservation.getRoomNumber());

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        double foodCharges = 0;
        double serviceCharges = 0;
        double totalPaid = 0;

        if (storage != null) {

            List<String> foodRecords =
                    storage.readRecords("foodorders.txt");

            for (String record : foodRecords) {

                String[] parts = record.split(",", -1);

                if (parts.length >= 7 && parts[1].equalsIgnoreCase(reservation.getCustomerId()) && parts[2].equals(String.valueOf(reservation.getRoomNumber())) && !parts[6].equalsIgnoreCase("CANCELLED")) {

                    try {
                        foodCharges += Integer.parseInt(parts[4]) * Double.parseDouble(parts[5]);

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Invalid food order record skipped.");
                    }
                }
            }

            List<String> paymentRecords = storage.readRecords("payments.txt");

            for (String record : paymentRecords) {

                String[] parts = record.split(",", -1);

                if (parts.length >= 5 && parts[1].equalsIgnoreCase(bookingId) && parts[4].equalsIgnoreCase("SUCCESSFUL")) {

                    try {
                        totalPaid += Double.parseDouble(parts[2]);

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Invalid payment record skipped.");
                    }
                }
            }

            List<String> serviceRecords = storage.readRecords("services.txt");

            for (String record : serviceRecords) {

                String[] parts = record.split(",", -1);

                if (parts.length >= 5 && parts[1].equalsIgnoreCase(reservation.getCustomerId()) && parts[2].equals(String.valueOf(reservation.getRoomNumber())) && parts[4].equalsIgnoreCase("COMPLETED")) {

                    serviceCharges += facilityPrice(parts[3]);
                }
            }

        } else {
            totalPaid = reservation.getAdvanceAmount();
        }

        Invoice invoice = new Invoice("INV" + bookingId,bookingId,reservation.getTotalAmount(),foodCharges,serviceCharges,totalPaid);

        invoice.displayInvoice();

        if (invoice.calculateRemainingAmount() > 0) {
            System.out.println("Checkout cannot be completed because "+ "a balance is still due.");
            return;
        }

        reservation.setStatus("CHECKED_OUT");
        room.setStatus("AVAILABLE");

        reservationDesk.saveRooms();
        reservationDesk.saveReservations();

        System.out.println("Checkout completed successfully.");
        System.out.println("Room " + room.getRoomNumber() + " is now available.");
    }

    private double facilityPrice(String name) {

        switch (name.trim().toLowerCase()) {

            case "swimming pool":
                return 300;

            case "parking":
                return 100;

            case "wi-fi":
                return 0;

            case "pet facility":
                return 250;

            case "bar":
                return 500;

            case "meeting room":
                return 1000;

            case "spa":
                return 800;

            default:
                return 0;
        }
    }
}