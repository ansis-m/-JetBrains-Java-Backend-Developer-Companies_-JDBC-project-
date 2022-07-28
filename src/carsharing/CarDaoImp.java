package carsharing;

import java.sql.*;
import java.util.ArrayList;

public class CarDaoImp implements CarDao{

    private static String jdbcURL;
    private static Connection connection;
    private static final String tableName = "CAR";


    public CarDaoImp(String jdbcURL) {

        /* Initialize the database. Check if table exists. If no table, then create one. */
        this.jdbcURL = jdbcURL;
        openTable();
    }

    private void openTable() {
        try{
            Class.forName ("org.h2.Driver");
            connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(true);
            Statement st = connection.createStatement();
            /* Check if table exists. If it doesn't create it. */
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, new String[] {"TABLE"});
            if(!resultSet.next()){
                System.out.println("creating car table");
                st.executeUpdate("CREATE TABLE " + tableName + " (ID int auto_increment primary key, NAME varchar(250) unique not null, COMPANY_ID INT,\n" +
                        " CONSTRAINT xxx FOREIGN KEY (COMPANY_ID)\n" +
                        "    REFERENCES COMPANY(ID) );");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addCar(String name, int id) {

        try{
            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO " + tableName + " (NAME, COMPANY_ID) VALUES ('" + name + "', " + id + ");");
            System.out.println("The car was created!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Car> getAllCars() {
        return null;
    }

    @Override
    public ArrayList<Car> getCarsByCompanyID(int id) {
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
