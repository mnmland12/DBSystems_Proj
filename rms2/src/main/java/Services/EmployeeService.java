package Services;

import DataModel.*;
import DataAccess.*;
import java.util.List;

public class EmployeeService{
    private EmployeeConnect employeeConnect;

    public EmployeeService(EmployeeConnect employeeConnect){
        this.employeeConnect = employeeConnect;
    }
    
    //validate ID
    public boolean validateEmployeeID(int empID) {
    	if(employeeConnect.getEmployeeByID(empID) != null) {
    		return true;
    	}else {
    		return false;
    	}
    }

    //CRUD
    public void createEmployee(EmployeeModel employee){
        employeeConnect.createEmployee(employee);
    }

    public void deleteEmployee(int empID){
        employeeConnect.deleteEmployee(empID);
    }

    public EmployeeModel getEmployeeByID(int empID){
        return employeeConnect.getEmployeeByID(empID);
    }

    public List<EmployeeModel> getAllEmployees(){
        return employeeConnect.getAllEmployees();
    }

    public void updateEmployee(EmployeeModel employee){
        employeeConnect.updateEmployee(employee);
    }

    public void getEmployeeTransactions(int empID){
        //employeeConnect.getEmployeeTransactions(empID);
    }

    //additional functions
    public void addLocationToEmployee(EmployeeModel employee, LocationModel location){
        //employee.getLocation().add(location);
    }

    public void addTransactionToEmployee(EmployeeModel employee, TransactionModel transaction){
        employee.getTransaction().add(transaction);
    }

    public int getLocationByEmp(int empID){
        return employeeConnect.getLocationByEmp(empID);
    }
}