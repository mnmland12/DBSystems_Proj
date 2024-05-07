package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataAccess.DatabaseConnection;
import DataAccess.EmployeeConnect;
import DataAccess.TransactionConnect;
//import DataModel.EmployeeModel;
import DataModel.TransactionModel;

@WebServlet("/deleteEmployee")
public class deleteEmployee extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int empID = Integer.parseInt(request.getParameter("empID"));

        Connection conn = DatabaseConnection.getInstance();
        TransactionConnect tConn = new TransactionConnect(conn);
        ArrayList<Integer> tIDs = tConn.getTransactionIDByEmpID(empID);
        
        EmployeeConnect empConn = new EmployeeConnect(conn);
        //EmployeeModel emp = empConn.getEmployeeByID(empID);
        for(Integer i : tIDs){
            TransactionModel tModel = tConn.getTransactionByID(i);
            int locID = tModel.getLocation().getLocationID();
            int managerID = empConn.getManagerIDByLocationID(locID);
            tConn.updateTransaction(empID, managerID);
        }

        empConn.deleteEmployee(empID);
        System.out.println("Employee ID : " + empID + " deleted.");
       

        response.sendRedirect("employeeDeleted.jsp");
    }

}
