package carsharing;

import java.util.ArrayList;

public class Company {

    private String ID;
    private String NAME;
    private ArrayList<Car> cars;

    public Company(String ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;
        cars = new ArrayList<>();
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

    public void addCar(Car car){
        cars.add(car);
    }
}
