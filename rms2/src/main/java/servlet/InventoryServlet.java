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
import DataAccess.InventoryConnect;
import DataModel.InventoryModel;


@WebServlet("/inventorySearch")
public class InventoryServlet extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("In inventorySearch doGet");

        HttpSession session = request.getSession();
        int location = Integer.parseInt(request.getParameter("location"));
        Connection conn = DatabaseConnection.getInstance();
        InventoryConnect invConn = new InventoryConnect(conn);
        ArrayList<InventoryModel> invList = invConn.getInventoryByLocationID(location);

        System.out.println("inventory List Size:" + invList.size());

        session.setAttribute("inventoryList", invList);

        request.getRequestDispatcher("inventory.jsp").forward(request, response);

    }
}
