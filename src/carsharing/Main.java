package carsharing;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /* Get commandline argument and construct database URL*/

        String jdbcURL;
        if(args.length > 1 && args[0].equals("-databaseFileName"))
            jdbcURL = "jdbc:h2:./src/carsharing/db/" + args[1];
        else
            jdbcURL = "jdbc:h2:./src/carsharing/db/carsharing";


        /* Enter infinite loop */

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("1. Log in as a manager\n" +
                    "0. Exit");
            String input = scanner.nextLine();
            if (input.equals("0"))
                break;
            else if  (input.equals("1"))
                LogInAsManager(scanner, jdbcURL);
            
        }


        try{
            Class.forName ("org.h2.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(true);
            Statement st = connection.createStatement();
            st.executeUpdate("CREATE TABLE COMPANY (ID int primary key auto_increment, name varchar(250) unique not null);");

            //execute Q
//            ResultSet resultSet = st.executeQuery("query_placeholder");
//            while(resultSet.next()){
//                String data = resultSet.getString("column_name_placeholder");
//            }
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void LogInAsManager(Scanner scanner, String jdbcURL) {
        
        while(true){
            System.out.println("1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");
            String input = scanner.nextLine();
            if (input.equals("0"))
                return;
            else if  (input.equals("1"))
                PrintCompanyList(jdbcURL);
            else if (input.equals("2"))
                CreateCompany(jdbcURL);
        }
    }

    private static void CreateCompany(String jdbcURL) {
    }

    private static void PrintCompanyList(String jdbcURL) {
    }
}