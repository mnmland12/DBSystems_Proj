package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Services.*;
import DataAccess.*;
import DataModel.EmployeeModel;

@WebServlet("/chooseAction")
public class HomepageChoice extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        String loc = "";
        if (action != null) {
            switch (action) {
                case "startTransaction":
                    loc ="transaction.jsp";
                    break;
                case "searchProducts":
                    loc ="products.jsp";
                    break;
                case "getFutureInfo":
                    loc  ="getPastInfo";
                    break;
                case "getStockInfo":
                    loc ="getLocation";
                    break;
                case "getTransferForm":
                    loc = "getTransferForm";
                    break;
                case "homepage":
                    loc = "homepage.jsp";
                    break;
                case "getPastData":
                    loc = "recentTransactions";
                    break;
                case "manageEmployees":
                    loc = "getLocation2";
                    break;
                case "updateWarehouse":
                    loc = "updateWarehouse";
                    break;
                default:
                    // default???
                    break;
            }
        }

        response.sendRedirect(loc);
    }
}
