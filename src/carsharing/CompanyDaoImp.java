package carsharing;

import java.sql.*;
import java.util.ArrayList;

public class CompanyDaoImp implements CompanyDao{

    private static String jdbcURL;
    private static final String tableName = "COMPANY";
    private static Connection connection;

    public CompanyDaoImp(String jdbcURL) {

        this.jdbcURL = jdbcURL;

        /* Initialize the database. Check if table exists. If no table, then create one. */
        openTable();
    }

    @Override
    public void addCompany(String name) {

        try{
            Statement st = connection.createStatement();
            if(tableEmpty()){
                st.executeUpdate("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1;");
                st = connection.createStatement();
            }
            st.executeUpdate("INSERT INTO " + tableName + " (NAME) VALUES ('" + name + "');");
            System.out.println("The company was created!");
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
    public ArrayList<Company> getAllCompanies() {

        ArrayList<Company> result = new ArrayList<>();
        try{
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName + ";");
            while(resultSet.next()){
                result.add(new Company(resultSet.getString("ID"), resultSet.getString("NAME")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    @Override
    public ArrayList<Car> getCars() {
        /* To do */
        return null;
    }


    private static void openTable() {

        try{
            Class.forName ("org.h2.Driver");
            connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(true);
            Statement st = connection.createStatement();
            /* Check if table exists. If it doesn't create it. */
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, new String[] {"TABLE"});
            if(!resultSet.next()){
                st.executeUpdate("CREATE TABLE " + tableName + " (ID int auto_increment primary key, NAME varchar(250) unique not null);");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
    public Company getCompanyByID(int companyID) {

        try{
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName + " WHERE ID=" + companyID + ";");
            resultSet.next();
            return new Company(resultSet.getString("ID"), resultSet.getString("NAME"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
