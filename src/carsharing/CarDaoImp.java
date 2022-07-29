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
                st.executeUpdate("CREATE TABLE " + tableName + " (ID int auto_increment primary key, NAME varchar(250) unique not null, COMPANY_ID INT not null,\n" +
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Car> getAllCars() {
        ArrayList<Car> result = new ArrayList<>();
        try{
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName + ";");
            while(resultSet.next()){
                result.add(new Car(resultSet.getString("ID"), resultSet.getString("NAME"), (int) resultSet.getLong("COMPANY_ID")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    @Override
    public ArrayList<Car> getCarsByCompanyID(int id) {
        ArrayList<Car> result = new ArrayList<>();
        try{
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName + " WHERE COMPANY_ID = " + id + ";");
            while(resultSet.next()){
                result.add(new Car(resultSet.getString("ID"), resultSet.getString("NAME"), (int) resultSet.getLong("COMPANY_ID")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
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

    @Override
    public void dropTable() {
        try{
            Statement st = connection.createStatement();
            st.execute("DROP TABLE " + tableName + ";");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        openTable();
    }
}
