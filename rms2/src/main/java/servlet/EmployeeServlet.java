package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DataAccess.DatabaseConnection;
import DataAccess.EmployeeConnect;
import DataAccess.LocationConnect;
import DataModel.EmployeeModel;
import DataModel.LocationModel;

@WebServlet("/employeeServlet")
public class EmployeeServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        Connection conn = DatabaseConnection.getInstance();
        EmployeeConnect empConn = new EmployeeConnect(conn);
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        System.out.println("To delete " + firstName + " " + lastName);
        int empID = empConn.getIDByName(firstName, lastName);
        EmployeeModel emp = empConn.getEmployeeByID(empID);
        LocationModel eLoc = emp.getLocation();

        request.setAttribute("location", eLoc);
        request.setAttribute("employee", emp);

        request.getRequestDispatcher("confirmEmpDelete.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String position = request.getParameter("position");
        int locID = Integer.parseInt(request.getParameter("location"));

        EmployeeModel newEmp = new EmployeeModel();
        newEmp.setFirstName(firstName);
        newEmp.setLastName(lastName);
        newEmp.setPosition(position);

        Connection conn = DatabaseConnection.getInstance();
        LocationConnect locConn = new LocationConnect(conn);
        LocationModel lModel = locConn.getLocationByID(locID);
        newEmp.setLocation(lModel);
        EmployeeConnect empConn = new EmployeeConnect(conn);
        empConn.createEmployee(newEmp);

        int empID = empConn.getIDByName(firstName, lastName);
        HttpSession session = request.getSession();
        session.setAttribute("empID", empID);

        request.getRequestDispatcher("ConfirmNewEmp.jsp").forward(request, response);

    }
    
}
