package carsharing;

public class Car {

    private String ID;
    private String name;
    private int companyID;

    public Car(String ID, String name, int companyID) {
        this.ID = ID;
        this.name = name;
        this.companyID = companyID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getName() {
        return name;
    }

    public void setNAME(String name) {
        this.name = name;
    }
}
