package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataModel.EmployeeModel;
import DataModel.LocationModel;

public class EmployeeConnect implements EmployeeRepository {
    private Connection connection;

    public EmployeeConnect(Connection connection){
        this.connection = connection;
    }

    public int getLocationByEmp(int empID){

        String sql = "SELECT * FROM employeelocationassignment WHERE empID = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, empID);

            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return -1;
                }

                return rs.getInt("locationid");
            }
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
    
    public void createEmployee(EmployeeModel employee){
        String fn = employee.getFirstName();
        String ln = employee.getLastName();
        String p = employee.getPosition();
        //ArrayList<LocationModel> l = employee.getLocation();
        int loc = employee.getLocation().getLocationID();
        
        String sql = "INSERT INTO employee (firstname, lastname, position)"+
                    " VALUES (?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, fn);
            stmt.setString(2, ln);
            stmt.setString(3, p);

            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("Employee Created Successfully.");
            }else{
                System.out.println("Employee Creation Unsuccessful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        sql = "insert into employeelocationassignment (empid, locationid) values (?, ?)";
        int empID = getIDByName(fn, ln);
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, empID);
            stmt.setInt(2, loc);

            int ra = stmt.executeUpdate();
            if (ra > 0) {
                System.out.println("EmployeeLocationAssignment Created Successfully.");
            } else {
                System.out.println("EmployeeLocationAssignment Creation Unsuccessful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public EmployeeModel getEmployeeByID(int empID){
        String sql = "SELECT * FROM employee WHERE empid = ?";
        EmployeeModel employee = new EmployeeModel();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, empID);
            try(ResultSet rs = stmt.executeQuery()){
            if(!rs.next()){
                return null;
            }
            employee.setID(empID);
            employee.setFirstName(rs.getString("firstname"));
            employee.setLastName( rs.getString("lastname"));
            employee.setPosition(rs.getString("position"));
            LocationConnect lConn = new LocationConnect(connection);
            int lID = lConn.getLocationByEmpID(empID);
            LocationModel lModel = lConn.getLocationByID(lID);
            employee.setLocation(lModel);

            return employee;
            
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<EmployeeModel> getAllEmployees(){
        ArrayList<EmployeeModel> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        try(PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                EmployeeModel employee = new EmployeeModel();

                employee.setFirstName(rs.getString("firstname"));
                employee.setLastName(rs.getString("lastname"));
                employee.setID(rs.getInt("empid"));

                employees.add(employee);
            }
        }catch(SQLException e){
                e.printStackTrace();
            }

        return employees;
    }

    public void updateEmployee(EmployeeModel employee){
        //needs implemeneted
    }

    public void deleteEmployee(int empID){
        String sql = "delete from employeelocationassignment where empid=?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, empID);
            int ra = stmt.executeUpdate();
            if (ra < 1) {
                System.out.println("EmployeeLocationAssignment Delete unsuccessful.");
            } else {
                System.out.println("EmployeeeLocationAssignment Deletion Successful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        sql = "DELETE FROM employee WHERE empid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, empID);
            int rowsChanged = stmt.executeUpdate();
            if (rowsChanged < 1) {
                System.out.println("Employee Delete unsuccessful.");
            }else{
                System.out.println("Employee Deletion Successful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIDByFirstName(String firstName){
        String sql = "SELECT * FROM employee WHERE firstname = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, firstName);
            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return 0;
                }
                return rs.getInt("empID");
            }
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public int getIDByName(String firstName, String lastname) {
        String sql = "SELECT * FROM employee WHERE firstname = ? AND lastname = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastname);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return 0;
                }
                return rs.getInt("empID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getManagerIDByLocationID(int locID){
        String sql = "select employee.empid from employee, employeelocationassignment where employeelocationassignment.empid = employee.empid AND employee.position = 'Manager'"
                    + " and employeelocationassignment.locationid = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, locID);
            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return -1;
                }
                return rs.getInt("empID");
            }
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
}
