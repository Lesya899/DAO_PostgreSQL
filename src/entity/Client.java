package entity;



public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String passport;
    private String phoneNumber;
    private int drivingExperience;

    public Client(int id, String firstName, String lastName, String passport, String phoneNumber, int drivingExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
        this.phoneNumber = phoneNumber;
        this.drivingExperience = drivingExperience;
    }

    public Client(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDrivingExperience() {
        return drivingExperience;
    }

    public void setDrivingExperience(int drivingExperience) {
        this.drivingExperience = drivingExperience;
    }

    @Override
    public String toString() {
        return "Client[" +
               "id=" + id +
               ", firstName='" + firstName +
               ", lastName='" + lastName +
               ", passport='" + passport  +
               ", phoneNumber='" + phoneNumber +
               ", drivingExperience=" + drivingExperience +
               ']';
    }
}
