package carsharing;

import java.util.ArrayList;

public interface CarDao {
    void addCar(String name);
    ArrayList<Car> getAllCars();
    ArrayList<Car> getCarsByCompanyID(int id);

    void closeTable();
}
