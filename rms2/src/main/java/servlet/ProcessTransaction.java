package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;

import DataAccess.DatabaseConnection;
import DataAccess.EmployeeConnect;
import DataAccess.InventoryConnect;
import DataAccess.LocationConnect;
import DataAccess.ProductConnect;
import DataModel.EmployeeModel;
import DataModel.InventoryModel;
import DataModel.LocationModel;
import DataModel.ProductModel;
import DataModel.ProductsSoldModel;
import DataModel.TransactionModel;
import Services.EmployeeService;
import Services.InventoryService;
import Services.ProductService;

@WebServlet("/processTransaction")
public class ProcessTransaction extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("In process transaction doGet");
        //get the locationID from session employeeID(definitely not efficient)
        HttpSession session = request.getSession();
        int empID = (int)session.getAttribute("empID");
        Connection conn = DatabaseConnection.getInstance();
        EmployeeConnect empConn = new EmployeeConnect(conn);
        EmployeeService empService = new EmployeeService(empConn);
        int locationID = empService.getLocationByEmp(empID);

        InventoryConnect invConn = new InventoryConnect(conn);
        InventoryService invService = new InventoryService(invConn);
        ArrayList<InventoryModel> invList = invService.getInventoryLocationID(locationID);
        HashMap<Integer, String> productMap = new HashMap<>();
        //ProductConnect productConn = new ProductConnect(conn);
        //ProductService productService = new ProductService(productConn);
        for(InventoryModel i : invList){
            ProductModel p = i.getProduct();
            String psize = p.getProductName() + " " + p.getSize();
            productMap.put(p.getProductID(), psize);
        }

        System.out.println("Product Map Size:" + productMap.size());

        session.setAttribute("productMap", productMap);

        request.getRequestDispatcher("transaction.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("in processTransaction DoPost.");
        Connection conn = DatabaseConnection.getInstance();
        ProductConnect pConn = new ProductConnect(conn);
        String[] selectedProducts = request.getParameterValues("selectedProducts");
        double total = 0;

        //items that need to be used later
        ArrayList<ProductModel> products = new ArrayList<>();
        ArrayList<ProductsSoldModel> productsSold = new ArrayList<>();
        TransactionModel tModel = new TransactionModel();

        if(selectedProducts != null){
            for(String productID : selectedProducts){
                String quantityParam = "quantity_" + productID;
                int quantity = Integer.parseInt(request.getParameter(quantityParam));
                
                ProductModel pModel = pConn.getProductByID(Integer.parseInt(productID));
                products.add(pModel);
                System.out.println("added a product model to the transaction");

                ProductsSoldModel PSModel = new ProductsSoldModel();
                PSModel.setPrice(pModel.getPrice() * quantity);
                PSModel.setQuantity(quantity);
                PSModel.setProductID(pModel.getProductID());
                productsSold.add(PSModel);
                System.out.println("added PSModel to transaction");

                total += quantity * pModel.getPrice();
            }
        }

        HttpSession session = request.getSession();

        tModel.setTotal(total);
        long mil = System.currentTimeMillis();
        Date d = new Date(mil);
        tModel.setDate(d);
        EmployeeConnect eConn = new EmployeeConnect(conn);
        System.out.println(session.getAttribute("empID"));
        EmployeeModel employee = eConn.getEmployeeByID((int)session.getAttribute("empID"));
        LocationConnect lConn = new LocationConnect(conn);
        System.out.println(employee.getEmpID());
        int locID = lConn.getLocationByEmpID(employee.getEmpID());
        LocationModel location = lConn.getLocationByID(locID);
        employee.setLocation(location);
        System.out.println(location.toString());
        tModel.setEmployee(employee);
        tModel.setLocation(location);

        session.setAttribute("selectedProducts", products);
        session.setAttribute("productsSoldModel", productsSold);
        session.setAttribute("transactionModel", tModel);


        response.sendRedirect("payment.jsp");
    }
}
