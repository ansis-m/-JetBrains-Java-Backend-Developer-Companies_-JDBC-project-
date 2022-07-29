package carsharing;

import java.util.ArrayList;

public interface CustomerDao {

    void addCustomer(String name);

    ArrayList<Customer> getAllCustomers();

    String getJdbcURL();

    void closeTable();

    Customer getCustomerByID(String customerId);

    void rentCar(String customerId, String companyID, String carID);

    void returnCar(String customerId);
}
