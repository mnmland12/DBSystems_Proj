package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DataAccess.DatabaseConnection;
import DataAccess.InventoryConnect;
import DataAccess.LocationConnect;
import DataAccess.ProductConnect;
import DataModel.InventoryModel;
import DataModel.ProductModel;

@WebServlet("/getTransferForm")
public class WarehouseRestock extends HttpServlet{
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String aRS = request.getParameter("aboveRestock");
        System.out.println("above restock :" + aRS);
        int above = Integer.parseInt(aRS);

        HttpSession session = request.getSession();
        ArrayList<InventoryModel> invList = (ArrayList<InventoryModel>)(session.getAttribute("inventoryList"));
        HashMap<ProductModel, Integer> restock = new HashMap<>(); 
        HashMap<Integer, String> restockError = new HashMap<>();
        Connection conn = DatabaseConnection.getInstance();
        InventoryConnect invConn = new InventoryConnect(conn);
        LocationConnect locConn = new LocationConnect(conn);
        ArrayList<InventoryModel> warehouseInv = invConn.getInventoryByLocationID(locConn.getIDbyLocationName("Warehouse"));

        for(int i = 0 ; i < invList.size() ; i++){
            InventoryModel inv = invList.get(i);
            InventoryModel warehouse = warehouseInv.get(i);
            ProductModel key = inv.getProduct();
            int iKey = key.getProductID();
            int value = 0;
            //System.out.println(warehouseInv.get(i).getProduct().getProductID() + " " + invList.get(i).getProduct()
            //        .getProductID() + " " + iKey);
            int restockWith = inv.getReorderLevel() - inv.getStockQuantity() + above;
            if(restockWith < 1){
                value = 0;
            }else if(restockWith <= warehouse.getStockQuantity()){
                value = restockWith;
            }else{
                value = warehouse.getStockQuantity();
                restockError.put(iKey, "Insufficient stock in warehouse to restock. Will restock with remaining warehouse stock :" + warehouse.getStockQuantity() + " units.");
            }

            //System.out.println(key.getProductID() + " " + value);
            restock.put(key, value);
        }

        request.setAttribute("warehouseInv", warehouseInv);
        request.setAttribute("inventoryList", invList);
        request.setAttribute("restockError", restockError);
        request.setAttribute("restockMap", restock);

        request.getRequestDispatcher("warehouse.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<InventoryModel> newLocInv = (ArrayList<InventoryModel>) session.getAttribute("newInventoryList");
        ArrayList<InventoryModel> newWarehouseInv = (ArrayList<InventoryModel>) session.getAttribute("newWarehouseInv");
        ArrayList<ProductModel> newProduct = new ArrayList<>();

        System.out.println(newLocInv.size() + " " + newWarehouseInv.size());
        Connection conn = DatabaseConnection.getInstance();
        InventoryConnect invConn = new InventoryConnect(conn);
        ProductConnect productConn = new ProductConnect(conn);
        int locID = newLocInv.get(0).getLocation().getLocationID();
        int[] ids = new int[newLocInv.size()];
        for(int i = 0 ; i < ids.length ; i++){
            InventoryModel l = newLocInv.getFirst();
            invConn.updateInventory(l);
            System.out.println("updated location inv: " + l.toString());
            newLocInv.removeFirst();
            InventoryModel w = newWarehouseInv.getFirst();
            invConn.updateInventory(w);
            System.out.println("updated warehouse inventory: " + w.toString());
            newWarehouseInv.removeFirst();
            ids[i] = l.getProduct().getProductID(); 
        }

        for(int i = 0 ; i < ids.length ; i++){
            InventoryModel inv = invConn.getInventoryModel(locID, ids[i]);
            newLocInv.add(inv);
            InventoryModel wInv = invConn.getInventoryModel(8, ids[i]);
            newWarehouseInv.add(wInv);
            ProductModel pM = productConn.getProductByID(ids[i]);
            newProduct.add(pM);
        }

        session.setAttribute("newInventoryList", newLocInv);
        session.setAttribute("newWarehouseInv", newWarehouseInv);
        session.setAttribute("newProductList", newProduct);

        request.getRequestDispatcher("sendRestock.jsp").forward(request, response);
    }
}
