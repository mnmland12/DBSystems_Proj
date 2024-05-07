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

@WebServlet("/getLocation")
public class LocationServlet extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("In location doGet");
        HttpSession session = request.getSession();
        
        Connection conn = DatabaseConnection.getInstance();
        LocationConnect locConn = new LocationConnect(conn);
        ArrayList<LocationModel> locList = locConn.getAllLocations();
        HashMap<Integer, String> locMap = new HashMap<>();
        for (LocationModel l : locList) {
            locMap.put(l.getLocationID(), l.getLocationName());
        }

        System.out.println("Location Map Size:" + locMap.size());

        session.setAttribute("locationMap", locMap);

        request.getRequestDispatcher("stock.jsp").forward(request, response);

    }
}
