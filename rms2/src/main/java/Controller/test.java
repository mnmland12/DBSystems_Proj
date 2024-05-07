package Controller;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import DataAccess.*;
import DataModel.*;
import Services.*;

public class test {
    public static void main(String[] args){
        Connection conn = DatabaseConnection.getInstance();
        EmployeeConnect ec = new EmployeeConnect(conn);
        EmployeeService es = new EmployeeService(ec);
        
        boolean v = es.validateEmployeeID(1);

        System.out.println(v);

        DatabaseConnection.close();
    }
}
