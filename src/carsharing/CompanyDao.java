package carsharing;

import java.util.ArrayList;

public interface CompanyDao {

    void addCompany(String name);
    ArrayList<Company> getAllCompanies();
}
