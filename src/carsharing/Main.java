package carsharing;

import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static String fileName;
    private static Scanner scanner;
    private static String jdbcURL;
    private static final String tableName = "COMPANY";


    public static void main(String[] args) {

        /* Get commandline argument and construct database URL*/
        if(args.length > 1 && args[0].equals("-databaseFileName")) {
            fileName = args[1];
            jdbcURL = "jdbc:h2:./src/carsharing/db/" + args[1];
        }
        else {
            fileName = "carsharing";
            jdbcURL = "jdbc:h2:./src/carsharing/db/carsharing";
        }

        /* Initialize the database */
        createTable();

        /* Enter infinite loop */
        scanner = new Scanner(System.in);
        while(true){
            System.out.println("1. Log in as a manager\n" +
                    "0. Exit");
            String input = scanner.nextLine();
            if (input.equals("0"))
                break;
            else if  (input.equals("1"))
                LogInAsManager();
        }
        scanner.close();
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

    private static void LogInAsManager() {
        
        while(true){
            System.out.println("\n1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                System.out.println();
                return;
            }
            else if  (input.equals("1"))
                PrintCompanyList();
            else if (input.equals("2"))
                CreateCompany();
        }
    }

    private static void CreateCompany() {

        System.out.println("\nEnter the company name:");
        String companyName = scanner.nextLine();

        try{
            Class.forName ("org.h2.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(true);
            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO " + tableName + " (NAME) VALUES ('" + companyName + "');");
            System.out.println("The company was created!");
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void PrintCompanyList() {

        try{
            Class.forName ("org.h2.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(true);
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName + ";");
            if(resultSet.next()) {
                System.out.println("\nCompany list:");
                System.out.printf("%s. %s", resultSet.getString("ID"), resultSet.getString("NAME"));
                while(resultSet.next()){
                    System.out.printf("\n%s. %s", resultSet.getString("ID"), resultSet.getString("NAME"));
                }
            }
            else
                System.out.printf("\nThe company list is empty!");
            System.out.println();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}