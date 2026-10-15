package operations;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;
import roomsystem.*;
import storage.*;

public class ReservationDesk {

    private List<RoomDetails> rooms;
    private List<Reservation> reservations;
    private FileStorage storage;

    public ReservationDesk() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public ReservationDesk(FileStorage storage) {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        this.storage = storage;
    }

    public void addRoom(RoomDetails room) {
        rooms.add(room);
    }

    public void displayAvailableRooms() {
        List<RoomDetails> availableRooms = rooms.stream().filter(RoomDetails::isAvailable).collect(Collectors.toList());

        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms.");
            return;
        }

        for (RoomDetails room : availableRooms) {
            room.displayRoom();
        }
    }

    public void searchRoomsByType(String roomType) {
        List<RoomDetails> matchingRooms = rooms.stream().filter(RoomDetails::isAvailable).filter(room -> room.getRoomType().equalsIgnoreCase(roomType)).collect(Collectors.toList());

        if (matchingRooms.isEmpty()) {
            System.out.println("No available rooms found for this type.");
            return;
        }

        for (RoomDetails room : matchingRooms) {
            room.displayRoom();
        }
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation added successfully.");
    }

    public void displayAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        for (Reservation reservation : reservations) {
            reservation.displayReservation();
        }
    }

    public Reservation findReservation(String bookingId) {
        for (Reservation reservation : reservations) {
            if (reservation.getBookingId().equalsIgnoreCase(bookingId)) {
                return reservation;
            }
        }

        return null;
    }

    public void cancelReservation(String bookingId) {
        Reservation reservation = findReservation(bookingId);

        if (reservation == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (!reservation.getStatus().equalsIgnoreCase("CONFIRMED")) {
            System.out.println("This booking cannot be cancelled.");
            return;
        }

        reservation.setStatus("CANCELLED");
        saveReservations();

        System.out.println("Reservation cancelled successfully.");
    }

    public List<RoomDetails> getRooms() {
        return rooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public RoomDetails findRoom(int roomNumber) {
        for (RoomDetails room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }

    public void saveRooms() {
        if (storage == null) {
            return;
        }

        List<String> records = new ArrayList<>();

        for (RoomDetails room : rooms) {
            records.add(room.getRoomNumber() + "," +room.getRoomType() + "," +room.getPricePerNight() + "," +room.getStatus());
        }

        storage.overwriteRecords("rooms.txt", records);
    }

    public void saveReservations() {
        if (storage == null) {
            return;
        }

        List<String> records = new ArrayList<>();

        for (Reservation reservation : reservations) {
            records.add(reservation.toRecord());
        }

        storage.overwriteRecords("bookings.txt", records);
    }

    public void loadRooms() {
        if (storage == null) {
            return;
        }

        rooms.clear();

        List<String> records = storage.readRecords("rooms.txt");

        for (String record : records) {
            try {
                String[] parts = record.split(",");

                if (parts.length >= 4) {
                    RoomDetails room = new RoomDetails(Integer.parseInt(parts[0]),parts[1],Double.parseDouble(parts[2]));
                    room.setStatus(parts[3]);
                    rooms.add(room);
                }
            } catch (Exception e) {
                System.out.println("Invalid room record skipped.");
            }
        }
    }

    public void loadReservations() {
        if (storage == null) {
            return;
        }

        reservations.clear();

        List<String> records = storage.readRecords("bookings.txt");

        for (String record : records) {
            try {
                reservations.add(Reservation.fromRecord(record));
            } catch (Exception e) {
                System.out.println("Invalid booking record skipped.");
            }
        }
    }

    public List<RoomDetails> findAvailableRooms(
            String roomType,
            LocalDate checkIn,
            LocalDate checkOut) {

        return rooms.stream().filter(room -> roomType.equalsIgnoreCase("ALL") || room.getRoomType().equalsIgnoreCase(roomType)).filter(room -> reservations.stream().noneMatch(reservation -> reservation.getRoomNumber() == room.getRoomNumber() && !reservation.getStatus().equalsIgnoreCase("CANCELLED") && !reservation.getStatus().equalsIgnoreCase("CHECKED_OUT") && checkIn.isBefore(reservation.getCheckOutDate()) && checkOut.isAfter(reservation.getCheckInDate()))).collect(Collectors.toList());
    }
}