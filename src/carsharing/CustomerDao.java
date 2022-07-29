package carsharing;

import java.util.ArrayList;

public interface CustomerDao {

    void addCustomer(String name);
    ArrayList<Customer> getAllCustomers();
    ArrayList<Customer> getCustomers();
    String getJdbcURL();

    void closeTable();

    Customer getCustomerByID(String customerId);
}
