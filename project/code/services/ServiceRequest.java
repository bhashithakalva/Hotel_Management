package services;

public class ServiceRequest {

    private String requestId;
    private String customerId;
    private int roomNumber;
    private String serviceName;
    private String status;

    public ServiceRequest(String requestId, String customerId, int roomNumber, String serviceName) {

        this.requestId = requestId;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.serviceName = serviceName;
        this.status = "PENDING";
    }

    public String toRecord() {
        return requestId + "," + customerId + "," + roomNumber + "," + serviceName + "," + status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayRequest() {
        System.out.println("Request ID: " + requestId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Service: " + serviceName);
        System.out.println("Status: " + status);
    }
}