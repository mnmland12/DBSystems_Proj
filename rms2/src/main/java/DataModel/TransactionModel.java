package DataModel;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;

import DataAccess.DatabaseConnection;
import DataAccess.TransactionConnect;

public class TransactionModel implements Serializable{
    private int transactionID;
    private Date date;
    private double total;
    private String paymentMethod;
    private EmployeeModel employee;
    private LocationModel location;
    private String notes;

    public TransactionModel(int transactionID, Date date, double total, String paymentMethod, EmployeeModel employee, LocationModel location){
        this.transactionID = transactionID;
        this.date = date;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.employee = employee;
        this.location = location;
    }

    public TransactionModel(int transactionID, Date date, double total, String paymentMethod, EmployeeModel employee, LocationModel location, String notes) {
        this.transactionID = transactionID;
        this.date = date;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.employee = employee;
        this.location = location;
        this.notes = notes;
    }

    public TransactionModel(){
        
    }

    //validation
    public boolean validateTransactionID(int transactionID){
        Connection conn = DatabaseConnection.getInstance();
        TransactionConnect t = new TransactionConnect(conn);
        if(t.getTransactionByID(transactionID) == null)
            return false;
        return true;
    }

    //toString
    public String toString(){
        return date.toString()+" "+total+" "+employee.getLastName()+" "+location.getLocationName();
    }
    //getters + setters

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    public void setDate(Date date){
        this.date = date;
    }

    public void setTotal(double total){
        this.total = total;
    }

    public void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
    }

    public void setEmployee(EmployeeModel employee){
        this.employee = employee;
    }

    public void setLocation(LocationModel location){
        this.location = location;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public int getTransactionID(){
        return transactionID;
    }

    public Date getDate(){
        return date;
    }

    public double getTotal(){
        return total;
    }

    public String getPaymentMethod(){
        return paymentMethod;
    }

    public EmployeeModel getEmployee(){
        return employee;
    }

    public LocationModel getLocation(){
        return location;
    }

    public String getNotes(){
        return notes;
    }
}
