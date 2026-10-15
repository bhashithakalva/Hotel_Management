package operations;

import roomsystem.RoomDetails;
import roomsystem.Reservation;
import storage.FileStorage;

public class CheckInDesk {

    private ReservationDesk reservationDesk;
    private FileStorage storage;

    public CheckInDesk(ReservationDesk reservationDesk) {
        this.reservationDesk = reservationDesk;
    }

    public CheckInDesk(ReservationDesk reservationDesk, FileStorage storage) {
        this.reservationDesk = reservationDesk;
        this.storage = storage;
    }

    public void checkIn(String bookingId) {
        Reservation reservation =
                reservationDesk.findReservation(bookingId);

        if (reservation == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (!reservation.getStatus().equalsIgnoreCase("CONFIRMED")) {
            System.out.println("Booking is not eligible for check-in.");
            return;
        }

        RoomDetails room = reservationDesk.findRoom(
                reservation.getRoomNumber());

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        room.setStatus("OCCUPIED");
        reservation.setStatus("CHECKED_IN");

        reservationDesk.saveRooms();
        reservationDesk.saveReservations();

        System.out.println("Check-in completed successfully.");
        System.out.println("Room Number: " + room.getRoomNumber());
    }
}