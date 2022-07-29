package carsharing;

public class Customer {

    private String ID;
    private String name;
    private int rentedCarID;

    public Customer(String id, String name, int rentedCarId) {
        this.ID = id;
        this.name = name;
        this.rentedCarID = rentedCarId;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRentedCarID() {
        return rentedCarID;
    }

    public void setRentedCarID(int rentedCarID) {
        this.rentedCarID = rentedCarID;
    }
}
