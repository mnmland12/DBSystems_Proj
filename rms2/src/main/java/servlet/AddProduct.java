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
import DataAccess.LocationConnect;
import DataAccess.ProductConnect;
import DataModel.ProductModel;
import DataModel.InventoryModel;
import DataModel.LocationModel;

@WebServlet("/addProduct")
public class AddProduct extends HttpServlet{
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        Connection conn = DatabaseConnection.getInstance();
        ProductConnect pConn = new ProductConnect(conn);
        LocationConnect locConn = new LocationConnect(conn);
        InventoryConnect invConn = new InventoryConnect(conn);
        
        String pName = (request.getParameter("newProductName"));
        Double price = (Double.parseDouble(request.getParameter("price")));
        String details = request.getParameter("details");
        String[] sizes = request.getParameterValues("sizes");
        ArrayList<ProductModel> productList = new ArrayList<>();
        for(int i = 0 ; i < sizes.length ; i++){
            ProductModel pModel = new ProductModel();
            pModel.setSize(sizes[i]);
            pModel.setProductName(pName);
            pModel.setPrice(price);
            if(details.length() > 0)
                pModel.setDescription(details);
            int pID = pConn.createProduct(pModel);
            pID = pConn.getProductID(pModel);
            pModel.setProductID(pID);
            System.out.println("Created product model: " + pModel.toString());
            productList.add(pModel);
        }
        System.out.println("Number of Product Models:" + productList.size());

        ArrayList<LocationModel> locList = locConn.getAllLocations();
        ArrayList<InventoryModel> invList = new ArrayList<>();
        for(LocationModel l : locList){
            int locID = l.getLocationID();
            int q = Integer.parseInt(request.getParameter("quantity"+locID));
            int restock = Integer.parseInt(request.getParameter("restockLevel"+locID));
            for(ProductModel p : productList){
                InventoryModel inv = new InventoryModel();
                inv.setLocation(l);
                inv.setProduct(p);
                inv.setStockQuantity(q);
                inv.setReorderLevel(restock);
                invConn.createInventory(inv);
                System.out.println("Added to inventory:"+inv.toString());
                invList.add(inv);
            }
        }

        request.setAttribute("productList", productList);

        request.getRequestDispatcher("ConfirmNewProduct.jsp").forward(request, response);

        
                
    }
}
