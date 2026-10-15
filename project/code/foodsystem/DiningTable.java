package foodsystem;

public class DiningTable {

    private int tableNumber;
    private int capacity;
    private String status;

    public DiningTable(int tableNumber, int capacity) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = "AVAILABLE";
    }

    // Getters
    public int getTableNumber() {
        return tableNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    // Update table status
    public void setStatus(String status) {
        this.status = status;
    }

    // Check table availability
    public boolean isAvailable() {
        return status.equals("AVAILABLE");
    }

    // Display table details
    public void displayTable() {
        System.out.println("----------------------------");
        System.out.println("Table Number : " + tableNumber);
        System.out.println("Capacity     : " + capacity + " people");
        System.out.println("Status       : " + status);
        System.out.println("----------------------------");
    }
}