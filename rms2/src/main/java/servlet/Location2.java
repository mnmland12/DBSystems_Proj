package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DataAccess.DatabaseConnection;
import DataAccess.LocationConnect;
import DataModel.LocationModel;

@WebServlet("/getLocation2")
public class Location2 extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("In location doGet");
        HttpSession session = request.getSession();
        
        Connection conn = DatabaseConnection.getInstance();
        LocationConnect locConn = new LocationConnect(conn);
        ArrayList<LocationModel> locList = locConn.getAllLocations();

        session.setAttribute("locations", locList);

        request.getRequestDispatcher("manageEmployee.jsp").forward(request, response);

    }
}
