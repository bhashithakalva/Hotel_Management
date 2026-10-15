package roomsystem;

public class RoomDetails {

    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private String status;

    public RoomDetails(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.status = "AVAILABLE";
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return status.equals("AVAILABLE");
    }

    public void displayRoom() {
        System.out.println("----------------------------");
        System.out.println("Room Number : " + roomNumber);
        System.out.println("Room Type   : " + roomType);
        System.out.println("Price/Night : Rs. " + pricePerNight);
        System.out.println("Status      : " + status);
        System.out.println("----------------------------");
    }
}