package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DataAccess.DatabaseConnection;
import DataAccess.InventoryConnect;
import DataModel.InventoryModel;

@WebServlet("/updateWarehouse")
public class UpdateWarehouseInv extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Connection conn = DatabaseConnection.getInstance();
        InventoryConnect invConn = new InventoryConnect(conn);
        ArrayList<InventoryModel> invList = invConn.getInventoryByLocationID(8);
        System.out.println(invList.size());

        HttpSession session = request.getSession();
        session.setAttribute("inventory", invList);

        request.getRequestDispatcher("warehouseRestock.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Enumeration<String> parameterNames = request.getParameterNames();

        Connection conn = DatabaseConnection.getInstance();
        InventoryConnect invConn = new InventoryConnect(conn);

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("quantity_")) {
                // Extract the product ID from the parameter name
                int productId = Integer.parseInt(paramName.substring("quantity_".length()));
                // Retrieve the new quantity value
                int newQuantity = Integer.parseInt(request.getParameter(paramName));

                InventoryModel i = invConn.getInventoryModel(8, productId);
                int oldQuantity = i.getStockQuantity();
                i.setStockQuantity(newQuantity);
                if(oldQuantity != newQuantity){
                    invConn.updateInventory(i);
                    System.out.println("Warehouse Stock of " + i.getProduct().getProductName() + " updated to quantity: " + i.getStockQuantity() );
                }
            }
        }

        response.sendRedirect("warehouseRestockConfirm.jsp");
    }
}
