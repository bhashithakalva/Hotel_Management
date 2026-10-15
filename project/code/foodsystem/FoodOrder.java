package foodsystem;

public class FoodOrder {

    private String orderId;
    private String customerId;
    private int roomNumber;
    private String foodItem;
    private int quantity;
    private double pricePerItem;
    private String status;

    public FoodOrder(String orderId, String customerId, int roomNumber,
                     String foodItem, int quantity, double pricePerItem) {

        this.orderId = orderId;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.status = "PLACED";
    }

    public String toRecord() {
        return orderId + "," + customerId + "," + roomNumber + "," + foodItem + "," + quantity + "," + pricePerItem + "," + status;
    }
    
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double calculateTotal() {
        return quantity * pricePerItem;
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Food Item: " + foodItem);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price Per Item: Rs. " + pricePerItem);
        System.out.println("Total Amount: Rs. " + calculateTotal());
        System.out.println("Order Status: " + status);
    }
}