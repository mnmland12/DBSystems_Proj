package DataAccess;

import DataModel.EmployeeModel;

import java.util.List;

public interface EmployeeRepository {
    void createEmployee(EmployeeModel employee);

    EmployeeModel getEmployeeByID(int empID);
    List<EmployeeModel> getAllEmployees();

    void updateEmployee(EmployeeModel employee);

    void deleteEmployee(int empID);

    int getIDByFirstName(String firstName);
}
