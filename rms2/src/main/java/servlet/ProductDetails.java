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
import DataAccess.InventoryConnect;
import DataAccess.ProductConnect;
import DataAccess.TransactionConnect;
import DataModel.InventoryModel;
import DataModel.ProductModel;
import DataModel.TransactionModel;

@WebServlet("/productDetailsServlet")
public class ProductDetails extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productID = Integer.parseInt(request.getParameter("productId"));

        Connection conn = DatabaseConnection.getInstance();
        ProductConnect pConn = new ProductConnect(conn);
        ProductModel product = pConn.getProductByID(productID);
        TransactionConnect tConn = new TransactionConnect(conn);
        ArrayList<TransactionModel> transactionList = tConn.getTransactionsByProductID(productID);
        InventoryConnect invConn = new InventoryConnect(conn);
        ArrayList<InventoryModel> invList = invConn.getInventoryByProductID(productID);

        request.setAttribute("inventoryList", invList);
        request.setAttribute("pTransactions", transactionList);
        request.setAttribute("product", product);

        request.getRequestDispatcher("productDetails.jsp").forward(request, response);
    }
}
