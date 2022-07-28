package carsharing;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static CompanyDao companyService;
    private static CarDao carService;
    private static String jdbcURL;



    public static void main(String[] args) {


        jdbcURL = initialize(args);

        /*create company table and get a service reference*/
        companyService = new CompanyDaoImp(jdbcURL);

        /*create car table and get a service reference*/
        carService = new CarDaoImp(jdbcURL);

        scanner = new Scanner(System.in);

        /* Enter infinite loop */
        while(true){
            System.out.println("1. Log in as a manager\n" +
                    "0. Exit");
            String input = scanner.nextLine();
            if (input.equals("0"))
                break;
            else if  (input.equals("1"))
                LogInAsManager();
        }
        companyService.closeTable();
        carService.closeTable();
        scanner.close();
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
                CompanyList();
            else if (input.equals("2"))
                CreateCompany();
        }
    }

    private static void CreateCompany() {

        System.out.println("\nEnter the company name:");
        String companyName = scanner.nextLine();
        companyService.addCompany(companyName);
    }

    private static void CompanyList() {

        ArrayList<Company> allCompanies = companyService.getAllCompanies();

        if(allCompanies.size() == 0){
            System.out.printf("\nThe company list is empty!");
            return;
        }
        while(true) {
            System.out.println("\nChose a company:");
            for(Company c : allCompanies) {
                System.out.printf("%s. %s\n", c.getID(), c.getNAME());
            }
            System.out.println("0. Back");
            String input = scanner.nextLine();
            if(input.equals("0"))
                return;
            try {
                int i = Integer.parseInt(input);
                System.out.printf("\n\'%s\' company\n", allCompanies.get(i - 1).getNAME());
                listCars(i);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private static void listCars(int id) {
        
        while(true){
            ArrayList<Car> cars = carService.getCarsByCompanyID(id);
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            String input = scanner.nextLine();
            if(input.equals("0"))
                return;
            else if (input.equals("1"))
                carList(cars);
            else if (input.equals("2"))
                createCar(id);
        }
    }

    private static void createCar(int id) {

        System.out.println("\nEnter the car name:");
        String name = scanner.nextLine();
        try{
            carService.addCar(name, id);
            System.out.println("The car was added!\n");
        }
        catch (Exception e){
            System.out.println("saving car failed!");
            e.printStackTrace();
        }

    }

    private static void carList(ArrayList<Car> cars) {
        if(cars == null || cars.size() == 0){
            System.out.println("The car list is empty!\n");
            return;
        }
        System.out.println("Car list:");
        for(Car c : cars){
            System.out.printf("%s. %s\n", c.getID(), c.getName());
        }
    }

    private static String initialize(String[] args) {

        if(args.length > 1 && args[0].equals("-databaseFileName")) {
            return "jdbc:h2:./src/carsharing/db/" + args[1];
        }
        else {
            return "jdbc:h2:./src/carsharing/db/carsharing";
        }
    }
}