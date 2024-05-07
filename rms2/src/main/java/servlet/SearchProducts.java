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
import javax.xml.crypto.Data;

import DataAccess.DatabaseConnection;
import DataAccess.ProductConnect;
import DataModel.ProductModel;

@WebServlet("/searchProduct")
public class SearchProducts extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("in searchProducts doGet");
        String query = request.getParameter("query");
        String size = request.getParameter("size");
        HttpSession session = request.getSession();

        System.out.println(query + " " + size);
        
        ArrayList<ProductModel> results = searchProducts(query, size);
        if(results != null)
            System.out.println("Search result entry size:" + results.size());
        
        session.setAttribute("searchResultsList", results);
        session.setAttribute("q", query);

        request.getRequestDispatcher("pSearchResults.jsp").forward(request, response);
    }

    private ArrayList<ProductModel> searchProducts(String query, String size){
        Connection conn = DatabaseConnection.getInstance();
        ProductConnect pConn = new ProductConnect(conn);
        ArrayList<ProductModel> result = new ArrayList<>();
        if(size != ""){
            result = pConn.getProductWSize(query, size);
        }else{
            result = pConn.getProductByName(query);
        }

        return result;
    }
}
