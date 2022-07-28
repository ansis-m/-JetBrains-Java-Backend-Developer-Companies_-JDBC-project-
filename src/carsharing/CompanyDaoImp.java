package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CompanyDaoImp implements CompanyDao{

    private static String jdbcURL;
    private static final String tableName = "COMPANY";

    public CompanyDaoImp(String[] args) {

        /* Get commandline argument and construct database URL*/
        initialize(args);

        /* Initialize the database. Check if table exists. If no table, then create one. */
        createTable();
    }

    @Override
    public void addCompany(String name) {

        try{
            Connection connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(true);
            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO " + tableName + " (NAME) VALUES ('" + name + "');");
            System.out.println("The company was created!");
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Company> getAllCompanies() {

        ArrayList<Company> result = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(true);
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName + ";");
            while(resultSet.next()){
                result.add(new Company(resultSet.getString("ID"), resultSet.getString("NAME")));
            }
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    private static void initialize(String[] args) {

        if(args.length > 1 && args[0].equals("-databaseFileName")) {
            jdbcURL = "jdbc:h2:./src/carsharing/db/" + args[1];
        }
        else {
            jdbcURL = "jdbc:h2:./src/carsharing/db/carsharing";
        }
    }

    private static void createTable() {

        try{
            Class.forName ("org.h2.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(true);
            Statement st = connection.createStatement();
            /* Check if table exists. If it doesn't create it. */
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, new String[] {"TABLE"});
            if(!resultSet.next()){
                st.executeUpdate("CREATE TABLE " + tableName + " (ID int auto_increment primary key, NAME varchar(250) unique not null);");
            }
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
