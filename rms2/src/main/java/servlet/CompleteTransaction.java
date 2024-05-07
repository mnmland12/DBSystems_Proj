package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DataAccess.DatabaseConnection;
import DataAccess.InventoryConnect;
import DataAccess.PSConnect;
import DataAccess.TransactionConnect;
import DataModel.InventoryModel;
import DataModel.ProductsSoldModel;
import DataModel.TransactionModel;

@WebServlet("/completeTransaction")
public class CompleteTransaction extends HttpServlet{
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("in complete transaction POST");
        HttpSession session = request.getSession();
        String payment = request.getParameter("paymentMethod");
        String notes = request.getParameter("transactionNote");
        Connection conn = DatabaseConnection.getInstance();
        PSConnect psConn = new PSConnect(conn);
        TransactionConnect tConn = new TransactionConnect(conn);
        InventoryConnect invConn = new InventoryConnect(conn);
        
        TransactionModel tModel = (TransactionModel) session.getAttribute("transactionModel");

        tModel.setPaymentMethod(payment);
        tModel.setNotes(notes);
        tConn.createTransaction(tModel);
        System.out.println("transaction created");
        int transactionID = tConn.getTransactionID(tModel);
        int locationID = tModel.getEmployee().getLocation().getLocationID();

        ArrayList<ProductsSoldModel> productSold = (ArrayList<ProductsSoldModel>) session.getAttribute("productsSoldModel");
        for(ProductsSoldModel psModel : productSold){
            psModel.setTransactionID(transactionID);
            psConn.createProductsSold(psModel);
            System.out.println("Products sold added");
            System.out.println("location ID: " + locationID);
            InventoryModel invModel = invConn.getInventoryModel(locationID, psModel.getProductID());
            System.out.println(invModel.toString());
            System.out.println("product level before:" + invModel.getStockQuantity());
            invModel.setStockQuantity(invModel.getStockQuantity() - psModel.getQuantity());
            System.out.println("product level after:" + invModel.getStockQuantity());
            invConn.updateInventory(invModel);
            System.out.println("inventory updated");
        }

        response.sendRedirect("transactionComplete.jsp");

    }
}
