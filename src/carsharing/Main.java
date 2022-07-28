package carsharing;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static CompanyDao companyService;

    public static void main(String[] args) {

        companyService = new CompanyDaoImp(args);
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
                PrintCompanyList();
            else if (input.equals("2"))
                CreateCompany();
        }
    }

    private static void CreateCompany() {

        System.out.println("\nEnter the company name:");
        String companyName = scanner.nextLine();
        companyService.addCompany(companyName);
    }

    private static void PrintCompanyList() {

        ArrayList<Company> allCompanies = companyService.getAllCompanies();

        if(allCompanies.size() == 0){
            System.out.printf("\nThe company list is empty!");
            return;
        }
        System.out.println("\nCompany list:");
        for(Company c : allCompanies) {
            System.out.printf("%s. %s\n", c.getID(), c.getNAME());
        }
    }
}