package carsharing;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarDaoImp implements CarDao{

    private static String jdbcURL;
    private static Connection connection;
    private static final String tableName = "CAR";


    public CarDaoImp(String jdbcURL) {

        /* Initialize the database. Check if table exists. If no table, then create one. */
        this.jdbcURL = jdbcURL;
        createTable();
    }

    private void createTable() {
    }


    @Override
    public void addCar(String name) {

    }

    @Override
    public ArrayList<Car> getAllCars() {
        return null;
    }

    @Override
    public void closeTable() {

        try{
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
