package carsharing;

public class Company {

    private String ID;
    private String NAME;

    public Company(String ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;
    }

    public Company() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
