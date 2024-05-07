package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataModel.*;

public class InventoryConnect implements InventoryRepository{
    private Connection connection;

    public InventoryConnect(Connection connection){
        this.connection = connection;
    }
    @Override
    public void createInventory(InventoryModel inventory) {
        int l = inventory.getLocation().getLocationID();
        int p = inventory.getProduct().getProductID();
        int s = inventory.getStockQuantity();
        int r = inventory.getReorderLevel();

        String sql = "INSERT INTO inventory (locationid, productid, stockquantity, reorderlevel)"+
                    " VALUES (?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, l);
            stmt.setInt(2, p);
            stmt.setInt(3, s);
            stmt.setInt(4, r);

            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("Inventory Creation Successful.");
            }else{
                System.out.println("Inventory Creation Unsuccessful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<InventoryModel> getInventoryByProductID(int productID) {
        ArrayList<InventoryModel> inventory = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE productid = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, productID);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    InventoryModel i = new InventoryModel();
                    LocationConnect location = new LocationConnect(connection);
                    i.setLocation(location.getLocationByID(rs.getInt("locationID")));
                    ProductConnect product = new ProductConnect(connection);
                    i.setProduct(product.getProductByID(rs.getInt("productID")));
                    i.setReorderLevel(rs.getInt("reorderlevel"));
                    i.setStockQuantity(rs.getInt("stockquantity"));

                    inventory.add(i);
                }
                return inventory;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<InventoryModel> getInventoryByLocationID(int locationID) {
        ArrayList<InventoryModel> inventory = new ArrayList<>();
        String sql = "SELECT * FROM inventory JOIN location ON location.locationid = inventory.locationid AND location.locationid = ? ORDER BY inventory.productID ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, locationID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InventoryModel i = new InventoryModel();
                    LocationConnect location = new LocationConnect(connection);
                    i.setLocation(location.getLocationByID(rs.getInt("locationID")));
                    ProductConnect product = new ProductConnect(connection);
                    i.setProduct(product.getProductByID(rs.getInt("productID")));
                    i.setReorderLevel(rs.getInt("reorderlevel"));
                    i.setStockQuantity(rs.getInt("stockquantity"));

                    inventory.add(i);
                }
                return inventory;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateInventory(InventoryModel inventory) {
        String sql = "UPDATE inventory SET stockquantity = ? WHERE productid = ? AND locationid = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, inventory.getStockQuantity());
            stmt.setInt(2, inventory.getProduct().getProductID());
            stmt.setInt(3, inventory.getLocation().getLocationID());
            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("Inventory Update Successful.");
            }else{
                System.out.println("Inventory Update Unsuccessful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteInventory(int productID, int locationID) {
        String sql = "DELETE FROM inventory WHERE productid = ? and locationid = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, productID);
            stmt.setInt(2, locationID);
            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("Invenetory deletion successful");
            }else{
                System.out.println("Inventory deletion unsuccessful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public InventoryModel getInventoryModel(int locID, int productID){
        String sql = "SELECT * FROM inventory WHERE locationID = ? AND productID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, locID);
            stmt.setInt(2, productID);
            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return null;
                }
                InventoryModel i = new InventoryModel();
                LocationConnect locConn = new LocationConnect(connection);
                //System.out.println("getInventoryModel location id:" + locID);
                LocationModel lModel = locConn.getLocationByID(locID);
                i.setLocation(lModel);
                ProductConnect pConn = new ProductConnect(connection);
                i.setProduct(pConn.getProductByID(productID));
                i.setReorderLevel(rs.getInt("reorderlevel"));
                i.setStockQuantity(rs.getInt("stockquantity"));
                return i;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
