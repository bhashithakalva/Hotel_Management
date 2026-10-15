package profiles;

public class StaffProfile {

    private String staffId;
    private String name;
    private String role;
    private String phone;

    public StaffProfile(String staffId, String name, String role, String phone) {
        this.staffId = staffId;
        this.name = name;
        this.role = role;
        this.phone = phone;
    }
    
    public String getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    // Display staff details
    public void displayStaff() {
        System.out.println("Staff ID: " + staffId);
        System.out.println("Name: " + name);
        System.out.println("Role: " + role);
        System.out.println("Phone: " + phone);
    }
}