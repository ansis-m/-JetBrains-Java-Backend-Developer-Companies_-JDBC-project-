package carsharing;

import java.util.ArrayList;

public interface CarDao {
    void addCar(String name, int id);
    ArrayList<Car> getAllCars();
    ArrayList<Car> getCarsByCompanyID(int id);
    void closeTable();

    void dropTable();

    Car getCarByID(String id);

    int getCompanyIDfromCarID(int rentedCarID);
}
