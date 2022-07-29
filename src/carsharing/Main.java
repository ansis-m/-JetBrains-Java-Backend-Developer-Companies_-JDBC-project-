package carsharing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static Scanner scanner;
    private static CompanyDao companyService;
    private static CarDao carService;
    private static CustomerDao customerService;
    private static String jdbcURL;
    private static final String path = "jdbc:h2:./src/carsharing/db/";
    private static final String defaultFilename = "carsharing";



    public static void main(String[] args) {


        /* initialize path/filename */
        jdbcURL = initialize(args);

        /*create company table and get a service reference*/
        companyService = new CompanyDaoImp(jdbcURL);

        /*create car table and get a service reference*/
        carService = new CarDaoImp(jdbcURL);

        /*create customer table and get a service reference*/
        customerService = new CustomerDaoImp(jdbcURL);



        scanner = new Scanner(System.in);

        /* Enter infinite loop */
        while(true){
            System.out.println("1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit");
            String input = scanner.nextLine();
            if (input.equals("0"))
                break;
            else if  (input.equals("1"))
                LogInAsManager();
            else if (input.equals("2"))
                LogInAsCustomer();
            else if (input.equals("3"))
                CreateCustomer();
        }
        companyService.closeTable();
        carService.closeTable();
        scanner.close();
    }

    private static void CreateCustomer() {
        System.out.println("Enter the customer name:");
        String customer = scanner.nextLine();
        customerService.addCustomer(customer);
        System.out.println("The customer was added!\n");
    }

    private static void LogInAsCustomer() {

        ArrayList<Customer> list = customerService.getAllCustomers();

        if(list.size() == 0){
            System.out.println("The customer list is empty!\n");
            return;
        }
        System.out.println("Customer list:");
        for(Customer c : list){
            System.out.printf("%s. %s\n", c.getID(), c.getName());
        }
        System.out.println("0. Back\n");

        String input = scanner.nextLine();
        if (input.equals("0"))
            return;
        customerPage(input);
    }

    private static void customerPage(String customerId) {

        boolean returned = false;

        while(true) {
            Customer thisCustomer = customerService.getCustomerByID(customerId);
            //System.out.println("customer info: " + thisCustomer.getID() + "  " + thisCustomer.getName() + "  " + thisCustomer.getRentedCarID());
            System.out.println("1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");

            String input = scanner.nextLine();
            if(thisCustomer.getRentedCarID() == 0 && (input.equals("3")|| input.equals("2")))
                System.out.println("\nYou didn't rent a car!\n");
            else if (thisCustomer.getRentedCarID() != 0 && input.equals("1"))
                System.out.println("\nYou've already rented a car!\n");
            else if(input.equals("0"))
                return;
            else if(input.equals("1"))
                choseCompany(customerId);
            else if(input.equals("2")) {
                if(!returned){
                    customerService.returnCar(customerId);
                    System.out.println("You've returned a rented car!");
                    returned = true;
                }
                else
                    System.out.println("You've returned a rented car!");
            }

            else if(input.equals("3")) {
                Car rentedCar = carService.getCarByID(String.valueOf(thisCustomer.getRentedCarID()));
                Company thisCompany = companyService.getCompanyByID(rentedCar.getCompanyID());
                System.out.println("\nYou rented car:");
                System.out.println(rentedCar.getName());
                System.out.println("Company:");
                System.out.println(thisCompany.getNAME() + "\n");
            }

        }
    }

    private static void choseCompany(String customerId) {
        while(true) {
            ArrayList<Company> companies = companyService.getAllCompanies();
            System.out.println("\nChoose a company:");
            for(Company c : companies)
                System.out.printf("%s. %s\n", c.getID(), c.getNAME());
            System.out.println("0. Back");
            String input = scanner.nextLine();

            if (input.equals("0"))
                return;
            else {
                pickCar(customerId, input);
                return;
            }
        }
    }

    private static void pickCar(String customerId, String companyID) {

        try {
            ArrayList<Car> availableCars =  carService.getCarsByCompanyID(Integer.parseInt(companyID));
            Set<Integer> rentedCars = new HashSet<>();
            for(Customer c : customerService.getAllCustomers())
                rentedCars.add(c.getRentedCarID());
            availableCars.removeIf(x -> rentedCars.contains(Integer.parseInt(x.getID())));
            System.out.println("\nChoose a car:");
            for(Car c : availableCars)
                System.out.printf("%s. %s\n", c.getID(), c.getName());
            System.out.println("0. Back");

            String input = scanner.nextLine();
            if(input.equals("0"))
                return;
            else
                rentCar(customerId, companyID, input);
        }
        catch (Exception e) {
            System.out.println("Bad input!!!");
            e.printStackTrace();
            return;
        }
    }

    private static void rentCar(String customerId, String companyID, String carID) {

        customerService.rentCar(customerId, companyID, carID);
        Car car = carService.getCarByID(carID);

        System.out.println("You rented '" + car.getName() + "'");
    }

    private static void LogInAsManager() {
        
        while(true){
            System.out.println("\n1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");
            String input = scanner.nextLine();
            switch (input) {
                case "0":
                    System.out.println();
                    return;
                case "1":
                    CompanyList();
                    break;
                case "2":
                    CreateCompany();
                    break;
            }
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
            System.out.print("\nThe company list is empty!");
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
                System.out.printf("\n'%s' company\n", allCompanies.get(i - 1).getNAME());
                if(listCars(i) == 1) //!!!!!!jumping
                    return;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private static int listCars(int id) {
        
        while(true){
            ArrayList<Car> cars = carService.getCarsByCompanyID(id);
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            String input = scanner.nextLine();
            if(input.equals("0"))
                return 1;
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
        int i = 1;
        for(Car c : cars){
            System.out.printf("%d. %s\n", i++, c.getName());
        }
    }

    private static String initialize(String[] args) {

        if(args.length > 1 && args[0].equals("-databaseFileName")) {
            return path + args[1];
        }
        else {
            return path + defaultFilename;
        }
    }
}