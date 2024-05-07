package DataModel;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;

import DataAccess.*;

public class EmployeeModel implements Serializable{
    private int empID;
    private String firstName;
    private String lastName;
    private String position;
    private LocationModel location;
    private ArrayList<TransactionModel> transaction;

    public EmployeeModel(String firstName, String lastName, String position, LocationModel location, ArrayList<TransactionModel> transaction){
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.location = location;
        this.transaction = transaction;
    }

    public EmployeeModel(String firstName, String lastName, String position, LocationModel location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.location = location;
    }

    public EmployeeModel(){

    }

    //validation
    public boolean validateEmployeeID(int empID){
        Connection conn = DatabaseConnection.getInstance();
        EmployeeConnect e = new EmployeeConnect(conn);
        if(e.getEmployeeByID(empID) == null)
            return false;
        return true;
    }

    //to string
    public String toString(){
        return this.firstName+" "+this.lastName+" "+this.empID;
    }
    //getters and setters
    public void setID(int id){
        this.empID = id;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public void setLocation(LocationModel location){
        this.location = location;
    }

    public void setTransaction(ArrayList<TransactionModel> transaction){
        this.transaction = transaction;
    }

    public int getEmpID(){
        return empID;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPosition(){
        return position;
    }

    public LocationModel getLocation(){
        return location;
    }

    public ArrayList<TransactionModel> getTransaction(){
        return transaction;
    }
}