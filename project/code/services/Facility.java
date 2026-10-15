package services;

public class Facility {

    private String facilityName;
    private double price;
    private String status;

    public Facility(String facilityName, double price, String status) {
        this.facilityName = facilityName;
        this.price = price;
        this.status = status;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayFacility() {
        System.out.println("Facility: " + facilityName);
        System.out.println("Price: Rs. " + price);
        System.out.println("Status: " + status);
    }
}