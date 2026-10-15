package homepage;

import java.util.*;
import java.time.LocalDate;

import profiles.*;
import roomsystem.*;
import foodsystem.*;
import services.*;
import payments.*;
import operations.*;
import storage.*;
import contracts.*;
import exceptions.*;

public class HotelApp {
    private static Scanner sc = new Scanner(System.in);

    private static FileStorage storage = new FileStorage("data");

    private static ReservationDesk reservationDesk =
            new ReservationDesk(storage);

    private static CheckInDesk checkInDesk =
            new CheckInDesk(reservationDesk, storage);

    private static CheckOutDesk checkOutDesk =
            new CheckOutDesk(reservationDesk, storage);

    private static Map<String, GuestProfile> guests =
            new LinkedHashMap<>();

    private static List<DiningTable> tables = new ArrayList<>();
    private static List<TableReservation> tableReservations = new ArrayList<>();
    private static List<FoodOrder> foodOrders = new ArrayList<>();
    private static List<Facility> facilities = new ArrayList<>();
    private static List<ServiceRequest> serviceRequests = new ArrayList<>();
    private static List<PaymentRecord> payments = new ArrayList<>();
    private static List<RefundRecord> refunds = new ArrayList<>();
    private static List<String> reviews = new ArrayList<>();

    public static void main(String[] args) {

        initialize();

        int choice;

        do {
            System.out.println("\n========== HOTEL MANAGEMENT SYSTEM ==========");
            System.out.println("1. User / Guest");
            System.out.println("2. Admin / Staff");
            System.out.println("3. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    guestMenu();
                    break;

                case 2:
                    staffMenu();
                    break;

                case 3:
                    System.out.println("Thank you for visiting!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);

        sc.close();
    }

    private static void initialize() {
        reservationDesk.loadRooms();
        reservationDesk.loadReservations();

        if (reservationDesk.getRooms().isEmpty()) {
            reservationDesk.addRoom(new RoomDetails(101, "AC", 2000));
            reservationDesk.addRoom(new RoomDetails(102, "Non-AC", 1200));
            reservationDesk.addRoom(new RoomDetails(103, "Premium", 3500));
            reservationDesk.addRoom(new RoomDetails(104, "Executive", 5000));
            reservationDesk.addRoom(new RoomDetails(105, "AC", 2200));
            reservationDesk.saveRooms();
        }

        tables.add(new DiningTable(1, 2));
        tables.add(new DiningTable(2, 4));
        tables.add(new DiningTable(3, 6));
        tables.add(new DiningTable(4, 4));

        facilities.add(new Facility("Swimming Pool", 300, "AVAILABLE"));
        facilities.add(new Facility("Parking", 100, "AVAILABLE"));
        facilities.add(new Facility("Wi-Fi", 0, "AVAILABLE"));
        facilities.add(new Facility("Pet Facility", 250, "AVAILABLE"));
        facilities.add(new Facility("Bar", 500, "AVAILABLE"));
        facilities.add(new Facility("Meeting Room", 1000, "AVAILABLE"));
        facilities.add(new Facility("Spa", 800, "AVAILABLE"));

        for (String record : storage.readRecords("guests.txt")) {
            String[] parts = record.split(",", -1);
            if (parts.length >= 4) {
                guests.put(parts[0], new GuestProfile(parts[0], parts[1], parts[2], parts[3]));
            }
        }

        for (String record : storage.readRecords("payments.txt")) {
            String[] parts = record.split(",", -1);
            if (parts.length >= 5) {
                try {
                    payments.add(new PaymentRecord(parts[0], parts[1],
                            Double.parseDouble(parts[2]), parts[3], parts[4]));
                } catch (RuntimeException e) {
                    System.out.println("Invalid payment record skipped.");
                }
            }
        }

        for (String record : storage.readRecords("refunds.txt")) {
            String[] parts = record.split(",", -1);
            if (parts.length >= 5) {
                try {
                    refunds.add(new RefundRecord(parts[0], parts[1],
                            Double.parseDouble(parts[2]), parts[3], LocalDate.parse(parts[4])));
                } catch (RuntimeException e) {
                    System.out.println("Invalid refund record skipped.");
                }
            }
        }

        for (String record : storage.readRecords("foodorders.txt")) {
            String[] parts = record.split(",", -1);
            if (parts.length >= 7) {
                try {
                    FoodOrder order = new FoodOrder(parts[0], parts[1], Integer.parseInt(parts[2]),
                            parts[3], Integer.parseInt(parts[4]), Double.parseDouble(parts[5]));
                    order.setStatus(parts[6]);
                    foodOrders.add(order);
                } catch (RuntimeException e) {
                    System.out.println("Invalid food order record skipped.");
                }
            }
        }

        for (String record : storage.readRecords("services.txt")) {
            String[] parts = record.split(",", -1);
            if (parts.length >= 5) {
                ServiceRequest request = new ServiceRequest(parts[0], parts[1],
                        Integer.parseInt(parts[2]), parts[3]);
                request.setStatus(parts[4]);
                serviceRequests.add(request);
            }
        }

        reviews.addAll(storage.readRecords("reviews.txt"));
    }

    private static String readText(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    private static LocalDate readDate(String message) {
        java.time.format.DateTimeFormatter formatter =
            java.time.format.DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(
                            java.time.format.ResolverStyle.STRICT);

        while (true) {
            try {
                return LocalDate.parse(
                        readText(message + " (DD-MM-YYYY): "),
                        formatter
                );
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println(
                        "Invalid date. Enter the date as DD-MM-YYYY.");
            }
        }
    }

    private static void guestMenu() {

        int choice;

        do {
            System.out.println("\n========== GUEST MENU ==========");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View Booking");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Restaurant & Table Reservation");
            System.out.println("6. Food & Room Service");
            System.out.println("7. Hotel Facilities");
            System.out.println("8. View Bill");
            System.out.println("9. Make Payment");
            System.out.println("10. Booking History");
            System.out.println("11. Reviews & Feedback");
            System.out.println("12. Back to Main Menu");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    searchRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    viewBooking();
                    break;

                case 4:
                    cancelBooking();
                    break;

                case 5:
                    reserveTable();
                    break;

                case 6:
                    orderFoodOrService();
                    break;

                case 7:
                    showFacilities();
                    break;

                case 8:
                    viewBill();
                    break;

                case 9:
                    makePayment();
                    break;

                case 10:
                    bookingHistory();
                    break;

                case 11:
                    addReview();
                    break;

                case 12:
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 12);
    }

    private static void staffMenu() {

        int choice;

        do {
            System.out.println("\n========== ADMIN / STAFF MENU ==========");
            System.out.println("1. Booking Management");
            System.out.println("2. Check-in Management");
            System.out.println("3. Check-out Management");
            System.out.println("4. Customer Management");
            System.out.println("5. Restaurant & Table Management");
            System.out.println("6. Food & Service Management");
            System.out.println("7. Hotel Facilities Management");
            System.out.println("8. Payment & Refund Management");
            System.out.println("9. Review Management");
            System.out.println("10. Reports");
            System.out.println("11. View All Records");
            System.out.println("12. Back to Main Menu");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    reservationDesk.displayAllReservations();
                    break;

                case 2:
                    checkInDesk.checkIn(readText("Enter Booking ID: "));
                    break;

                case 3:
                    checkOutDesk.checkOut(readText("Enter Booking ID: "));
                    break;

                case 4:
                    showGuests();
                    break;

                case 5:
                    showTables();
                    break;

                case 6:
                    showFoodAndRequests();
                    break;

                case 7:
                    showFacilities();
                    break;

                case 8:
                    showPaymentsAndRefunds();
                    break;

                case 9:
                    showReviews();
                    break;

                case 10:
                    reports();
                    break;

                case 11:
                    viewAllRecords();
                    break;

                case 12:
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 12);
    }

    private static void searchRooms() {

        String type = readText("Enter room type (AC / Non-AC / Premium / Executive / ALL): ");

        reservationDesk.searchRoomsByType(type);
    }

    private static void bookRoom() {

        String customerId = readText("Enter Customer ID or NEW to register: ");

        if (customerId.equalsIgnoreCase("NEW")) {

            String name = readText("Enter name: ");
            String phone = readText("Enter phone: ");
            String email = readText("Enter email: ");

            customerId = "G" + (guests.size() + 101);

            GuestProfile guest = new GuestProfile(customerId, name, phone, email);

            guests.put(customerId, guest);

            storage.saveRecord("guests.txt",customerId + "," + name + "," + phone + "," + email);

            System.out.println("Your Customer ID is: " + customerId);

        } else if (!guests.containsKey(customerId)) {

            System.out.println("Customer not found. Register first.");
            return;
        }

        LocalDate checkIn = readDate("Check-in date");
        LocalDate checkOut = readDate("Check-out date");

        if (checkIn.isBefore(LocalDate.now()) || !checkOut.isAfter(checkIn)) {
            System.out.println("Invalid booking dates.");
            return;
        }

        String type = readText("Enter room type: ");

        List<RoomDetails> availableRooms = reservationDesk.findAvailableRooms(type, checkIn, checkOut);

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for those dates.");
            return;
        }

        for (RoomDetails room : availableRooms) {
            room.displayRoom();
        }

        int roomNumber = readInt("Choose room number: ");

        RoomDetails selectedRoom = null;

        for (RoomDetails room : availableRooms) {
            if (room.getRoomNumber() == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Invalid room selection.");
            return;
        }

        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);

        double total = nights * selectedRoom.getPricePerNight();

        System.out.println("Total room cost: Rs. " + total);

        double advance = readDouble("Enter advance amount: ");

        if (advance > total) {
            System.out.println("Advance cannot exceed total amount.");
            return;
        }

        String bookingId = "B" + (reservationDesk.getReservations().size() + 101);

        Reservation reservation = new Reservation(bookingId,customerId,roomNumber,checkIn,checkOut,selectedRoom.getPricePerNight(),advance);

        reservationDesk.addReservation(reservation);
        reservationDesk.saveReservations();

        if (advance > 0) {
            PaymentRecord payment = new PaymentRecord("P" + (payments.size() + 101),bookingId,advance,"ADVANCE");
            payments.add(payment);
            storage.saveRecord("payments.txt", payment.toRecord());
        }
        System.out.println("Booking confirmed!");
        System.out.println("Booking ID: " + bookingId);
    }

    private static void viewBooking() {

        String id = readText("Enter Booking ID: ");

        Reservation reservation = reservationDesk.findReservation(id);

        if (reservation == null) {
            System.out.println("Booking not found.");
        } else {
            reservation.displayReservation();
        }
    }

    private static void cancelBooking() {
        String id = readText("Enter Booking ID: ");
        Reservation reservation = reservationDesk.findReservation(id);

        if (reservation == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (!reservation.getStatus().equalsIgnoreCase("CONFIRMED")) {
            System.out.println("This booking cannot be cancelled.");
            return;
        }

        for (RefundRecord existingRefund : refunds) {
            if (existingRefund.getBookingId().equalsIgnoreCase(id)) {
                System.out.println("A refund record already exists for this booking.");
                return;
            }
        }

        long days = java.time.temporal.ChronoUnit.DAYS.between(
                LocalDate.now(), reservation.getCheckInDate());

        double paidAmount = payments.stream().filter(p -> p.getBookingId().equalsIgnoreCase(id)).filter(p -> p.getPaymentStatus().equalsIgnoreCase("SUCCESSFUL")).mapToDouble(PaymentRecord::getAmount).sum();

        double refundAmount;
        if (days > 2) {
            refundAmount = paidAmount;
        } else if (days >= 1) {
            refundAmount = paidAmount * 0.5;
        } else {
            refundAmount = 0;
        }

        reservation.setStatus("CANCELLED");
        reservationDesk.saveReservations();

        if (refundAmount > 0) {
            RefundRecord refund = new RefundRecord("R" + (refunds.size() + 101), id, refundAmount);
            refunds.add(refund);
            storage.saveRecord("refunds.txt", refund.toRecord());
        }

        System.out.println("Booking cancelled.");
        System.out.println("Total successful payments: Rs. " + paidAmount);
        System.out.println("Refund amount: Rs. " + refundAmount);
        if (refundAmount > 0) {
            System.out.println("Refund status: PENDING");
        }
    }

    private static void reserveTable() {

        showTables();

        String customerId = readText("Enter Customer ID: ");

        if (!guests.containsKey(customerId)) {
            System.out.println("Customer not found.");
            return;
        }

        int tableNumber = readInt("Enter table number: ");
        int people = readInt("Number of people: ");

        DiningTable selected = null;

        for (DiningTable table : tables) {
            if (table.getTableNumber() == tableNumber&& table.isAvailable()&& people > 0&& people <= table.getCapacity()) {
                selected = table;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Table unavailable or invalid.");
            return;
        }

        LocalDate date = readDate("Reservation date");

        String id = "T" + (tableReservations.size() + 101);

        TableReservation reservation = new TableReservation(id, customerId, tableNumber, date, people);

        tableReservations.add(reservation);
        selected.setStatus("RESERVED");

        storage.saveRecord("dining.txt", reservation.toRecord());

        System.out.println("Table reserved successfully.");
        System.out.println("Reservation ID: " + id);
    }

    private static void orderFoodOrService() {
        System.out.println("1. Order Food");
        System.out.println("2. Request Room Service");
        int choice = readInt("Enter choice: ");
        String customerId = readText("Enter Customer ID: ");

        if (!guests.containsKey(customerId)) {
            System.out.println("Customer not found.");
            return;
        }

        int roomNumber = readInt("Enter room number: ");
        boolean validStay = reservationDesk.getReservations().stream().anyMatch(r -> r.getCustomerId().equalsIgnoreCase(customerId) && r.getRoomNumber() == roomNumber && (r.getStatus().equalsIgnoreCase("CONFIRMED") || r.getStatus().equalsIgnoreCase("CHECKED_IN")));

        if (!validStay) {
            System.out.println("Enter a room assigned to your confirmed booking.");
            return;
        }

        if (choice == 1) {
            System.out.println("1. Veg Biryani - Rs. 200");
            System.out.println("2. Dosa - Rs. 80");
            System.out.println("3. Meals - Rs. 150");
            System.out.println("4. Tea - Rs. 30");
            int itemChoice = readInt("Select food: ");
            int quantity = readInt("Enter quantity: ");
            String item;
            double price;

            switch (itemChoice) {
                case 1: item = "Veg Biryani"; price = 200; break;
                case 2: item = "Dosa"; price = 80; break;
                case 3: item = "Meals"; price = 150; break;
                case 4: item = "Tea"; price = 30; break;
                default:
                    System.out.println("Invalid food choice.");
                    return;
            }

            if (quantity <= 0) {
                System.out.println("Quantity must be positive.");
                return;
            }

            FoodOrder order = new FoodOrder("F" + (foodOrders.size() + 101),customerId, roomNumber, item, quantity, price);
            foodOrders.add(order);
            storage.saveRecord("foodorders.txt", order.toRecord());
            System.out.println("Food order placed.");
            System.out.println("Total: Rs. " + order.calculateTotal());
        } else if (choice == 2) {
            String serviceName = readText("Enter requested service: ");
            ServiceRequest request = new ServiceRequest("SR" + (serviceRequests.size() + 101),customerId, roomNumber, serviceName);
            serviceRequests.add(request);
            storage.saveRecord("services.txt", request.toRecord());
            System.out.println("Service request submitted. It will be billed after completion.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void showFacilities() {
        for (Facility facility : facilities) {
            facility.displayFacility();
        }
    }

    private static void viewBill() {
        String id = readText("Enter Booking ID: ");
        Reservation reservation = reservationDesk.findReservation(id);

        if (reservation == null) {
            System.out.println("Booking not found.");
            return;
        }

        double foodCharges = calculateFoodCharges(reservation);
        double serviceCharges = calculateServiceCharges(reservation);
        double paid = successfulPaymentsFor(id);

        Invoice invoice = new Invoice("INV" + id, id, reservation.getTotalAmount(), foodCharges, serviceCharges, paid);
        invoice.displayInvoice();
    }

    private static void makePayment() {
        String id = readText("Enter Booking ID: ");
        Reservation reservation = reservationDesk.findReservation(id);

        if (reservation == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (reservation.getStatus().equalsIgnoreCase("CANCELLED") || reservation.getStatus().equalsIgnoreCase("CHECKED_OUT")) {
            System.out.println("Payment is not allowed for a cancelled or checked-out booking.");
            return;
        }

        double foodCharges = calculateFoodCharges(reservation);
        double serviceCharges = calculateServiceCharges(reservation);
        double paid = successfulPaymentsFor(id);

        Invoice invoice = new Invoice("INV" + id, id, reservation.getTotalAmount(), foodCharges, serviceCharges, paid);
        double remaining = invoice.calculateRemainingAmount();
        System.out.println("Total bill: Rs. " + invoice.calculateAmount());
        System.out.println("Successful payments: Rs. " + paid);
        System.out.println("Remaining amount: Rs. " + remaining);

        if (remaining <= 0) {
            System.out.println("No payment due.");
            return;
        }

        double amount = readDouble("Enter payment amount: ");
        if (amount <= 0 || amount > remaining) {
            System.out.println("Enter a positive amount not exceeding the remaining balance.");
            return;
        }

        PaymentRecord payment = new PaymentRecord("P" + (payments.size() + 101), id, amount, "PAYMENT");
        payments.add(payment);
        storage.saveRecord("payments.txt", payment.toRecord());
        System.out.println("Payment recorded successfully.");
        System.out.println("Balance remaining: Rs. " + (remaining - amount));
    }

    private static double successfulPaymentsFor(String bookingId) {
        return payments.stream().filter(p -> p.getBookingId().equalsIgnoreCase(bookingId)).filter(p -> p.getPaymentStatus().equalsIgnoreCase("SUCCESSFUL")).mapToDouble(PaymentRecord::getAmount).sum();
    }

    private static double calculateFoodCharges(Reservation reservation) {
        return foodOrders.stream().filter(o -> o.getCustomerId().equalsIgnoreCase(reservation.getCustomerId())).filter(o -> o.getRoomNumber() == reservation.getRoomNumber()).filter(o -> !o.getStatus().equalsIgnoreCase("CANCELLED")).mapToDouble(FoodOrder::calculateTotal).sum();
    }

    private static double calculateServiceCharges(Reservation reservation) {
        double total = 0;
        for (ServiceRequest request : serviceRequests) {
            if (request.getCustomerId().equalsIgnoreCase(reservation.getCustomerId())&& request.getRoomNumber() == reservation.getRoomNumber()&& request.getStatus().equalsIgnoreCase("COMPLETED")) {
                for (Facility facility : facilities) {
                    if (facility.getFacilityName().equalsIgnoreCase(request.getServiceName())) {
                        total += facility.getPrice();
                        break;
                    }
                }
            }
        }
        return total;
    }

    private static void bookingHistory() {

        String customerId = readText("Enter Customer ID: ");
        boolean found = false;

        for (Reservation reservation : reservationDesk.getReservations()) {
            if (reservation.getCustomerId().equalsIgnoreCase(customerId)) {
                reservation.displayReservation();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No booking history found.");
        }
    }

    private static void addReview() {

        String customerId = readText("Enter Customer ID: ");

        if (!guests.containsKey(customerId)) {
            System.out.println("Customer not found.");
            return;
        }

        int rating = readInt("Enter rating (1-5): ");

        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }

        String feedback = readText("Enter feedback: ").replace(",", " ");

        String record = customerId + "," + rating + "," + feedback;

        reviews.add(record);
        storage.saveRecord("reviews.txt", record);

        System.out.println("Thank you for your feedback!");
    }

    private static void showGuests() {

        if (guests.isEmpty()) {
            System.out.println("No guest records.");
            return;
        }

        for (GuestProfile guest : guests.values()) {
            guest.displayGuest();
        }
    }

    private static void showTables() {
        for (DiningTable table : tables) {
            table.displayTable();
        }
    }

    private static void showFoodAndRequests() {

        for (FoodOrder order : foodOrders) {
            order.displayOrder();
        }

        for (ServiceRequest request : serviceRequests) {
            request.displayRequest();
        }

        if (foodOrders.isEmpty() && serviceRequests.isEmpty()) {
            System.out.println("No orders or service requests.");
        }
    }

    private static void showPaymentsAndRefunds() {

        for (PaymentRecord payment : payments) {
            payment.displayPayment();
        }

        for (RefundRecord refund : refunds) {
            refund.displayRefund();
        }

        if (payments.isEmpty() && refunds.isEmpty()) {
            System.out.println("No payment or refund records.");
        }
    }

    private static void showReviews() {

        if (reviews.isEmpty()) {
            System.out.println("No reviews yet.");
            return;
        }

        for (String review : reviews) {
            System.out.println(review);
        }
    }

    private static void reports() {
        long confirmed = reservationDesk.getReservations().stream().filter(r -> r.getStatus().equalsIgnoreCase("CONFIRMED")).count();
        long occupied = reservationDesk.getRooms().stream().filter(r -> r.getStatus().equalsIgnoreCase("OCCUPIED")).count();
        double totalPayments = payments.stream().filter(p -> p.getPaymentStatus().equalsIgnoreCase("SUCCESSFUL")).mapToDouble(PaymentRecord::getAmount).sum();
        double completedRefunds = refunds.stream().filter(r -> r.getRefundStatus().equalsIgnoreCase("COMPLETED")).mapToDouble(RefundRecord::getRefundAmount).sum();
        double pendingRefunds = refunds.stream().filter(r -> r.getRefundStatus().equalsIgnoreCase("PENDING")).mapToDouble(RefundRecord::getRefundAmount).sum();

        System.out.println("Confirmed bookings: " + confirmed);
        System.out.println("Occupied rooms: " + occupied);
        System.out.println("Total successful payments: Rs. " + totalPayments);
        System.out.println("Completed refunds: Rs. " + completedRefunds);
        System.out.println("Pending refunds: Rs. " + pendingRefunds);
        System.out.println("Net revenue after completed refunds: Rs. "+ (totalPayments - completedRefunds));
        System.out.println("Total food orders: " + foodOrders.size());
    }

    private static void viewAllRecords() {

        String[] files = {
            "guests.txt",
            "rooms.txt",
            "bookings.txt",
            "dining.txt",
            "foodorders.txt",
            "payments.txt",
            "refunds.txt",
            "services.txt",
            "reviews.txt"
        };

        for (String file : files) {
            System.out.println("\n========== " + file + " ==========");
            storage.displayRecords(file);
        }
    }
    private static int readInt(String message) {
    while (true) {
        System.out.print(message);

        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid integer.");
        }
    }
}

private static double readDouble(String message) {
    while (true) {
        System.out.print(message);

        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount.");
        }
    }
}
}