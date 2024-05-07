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
import DataAccess.PSConnect;
import DataAccess.TransactionConnect;
import DataModel.ProductModel;
import DataModel.TransactionModel;

@WebServlet("/recentTransactions")
public class RecentTransactions extends HttpServlet{
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Connection conn = DatabaseConnection.getInstance();
        TransactionConnect tConn = new TransactionConnect(conn);
        ArrayList<TransactionModel> transactionList = tConn.getAllTransactions();
        
        HttpSession session = request.getSession();
        session.setAttribute("transactionList", transactionList);

        request.getRequestDispatcher("salesData.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int transactionID = Integer.parseInt(request.getParameter("transactionID"));

        Connection conn = DatabaseConnection.getInstance();
        PSConnect psConn = new PSConnect(conn);
        HashMap<ProductModel, Integer> sold = psConn.getProductsByTransactionID(transactionID);
        TransactionConnect tConn = new TransactionConnect(conn);
        TransactionModel tMod = tConn.getTransactionByID(transactionID);

        HttpSession session = request.getSession();
        session.setAttribute("soldProducts", sold);
        session.setAttribute("transaction", tMod);

        request.getRequestDispatcher("transactionDetails.jsp").forward(request, response);
    }  
}