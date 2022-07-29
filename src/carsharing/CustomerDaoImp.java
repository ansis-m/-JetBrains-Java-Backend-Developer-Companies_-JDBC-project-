package carsharing;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDaoImp implements CustomerDao{


    private static String jdbcURL;
    private static final String tableName = "CUSTOMER";
    private static Connection connection;

    public CustomerDaoImp(String jdbcURL) {

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
                st.executeUpdate("CREATE TABLE " + tableName + " (ID int auto_increment primary key, NAME varchar(250) unique not null, RENTED_CAR_ID INT UNIQUE, " +
                        " CONSTRAINT yyy FOREIGN KEY (RENTED_CAR_ID)\n" +
                        "    REFERENCES CAR(ID) );");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addCustomer(String name) {

        try{
            Statement st = connection.createStatement();
            if(tableEmpty()){
                st.executeUpdate("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1;");
                st = connection.createStatement();
            }
            //st.executeUpdate("INSERT INTO " + tableName + " (NAME) VALUES ('" + name + "');");
            st.executeUpdate("INSERT INTO " + tableName + " (NAME, RENTED_CAR_ID) VALUES ('" + name + "', NULL" + ");");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tableEmpty() {

        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName);
            return !resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {

        ArrayList<Customer> result = new ArrayList<>();
        try{
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName + ";");
            while(resultSet.next()){
                result.add(new Customer(resultSet.getString("ID"), resultSet.getString("NAME"), (int) resultSet.getLong("RENTED_CAR_ID")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    @Override
    public ArrayList<Customer> getCustomers() {
        return null;
    }

    @Override
    public String getJdbcURL() {
        return null;
    }

    @Override
    public void closeTable() {

    }

    @Override
    public Customer getCustomerByID(String customerId) {
        try{
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName + " WHERE ID=" + customerId + ";");
            resultSet.next();
            return new Customer(resultSet.getString("ID"), resultSet.getString("NAME"), (int) resultSet.getLong("RENTED_CAR_ID"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void rentCar(String customerId, String companyID, String carID) {

        try{
            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE " + tableName + " SET RENTED_CAR_ID=" + Integer.parseInt(carID) + " WHERE ID=" + customerId + ";");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
