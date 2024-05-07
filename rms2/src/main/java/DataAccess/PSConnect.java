package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DataModel.ProductModel;
import DataModel.ProductsSoldModel;

public class PSConnect implements PSRepository{
    private Connection connection;

    public PSConnect(Connection connection){
        this.connection = connection;
    }

    @Override
    public void createProductsSold(ProductsSoldModel productSold) {
        int tID = productSold.getTransactionID();
        int pID = productSold.getProductID();
        int q = productSold.getQuantity();
        double p = productSold.getTotalPrice();

        String sql = "INSERT INTO productssold (transactionid, productid, quantity, totalprice)"+
                    " VALUES (?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, tID);
            stmt.setInt(2, pID);
            stmt.setInt(3, q);
            stmt.setDouble(4, p);

            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("ProductsSold Creation Successful.");
            }else{
                System.out.println("ProductsSold Creation Unsuccessful.");
            }
            

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ProductsSoldModel getProductsSoldModelByID(int saleID) {
        String sql = "SELECT * FROM productssold WHERE saleid = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, saleID);
            try(ResultSet rs = stmt.executeQuery()){
                ProductsSoldModel p = new ProductsSoldModel();
                p.setProductID(rs.getInt("productID"));
                p.setTransactionID(rs.getInt("transactionID"));
                p.setQuantity(rs.getInt("quantity"));
                p.setPrice(rs.getDouble("totalprice"));

                return p;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<ProductModel, Integer> getProductsByTransactionID(int transactionID){
        String sql = "SELECT product.productID, product.productName, productssold.quantity"+
                    " FROM productssold JOIN product ON productssold.productID = product.productID" +
                    " WHERE productssold.transactionID = ?";
        HashMap<ProductModel, Integer> pSold = new HashMap<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, transactionID);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    ProductConnect pConn = new ProductConnect(connection);
                    ProductModel pModel = pConn.getProductByID(rs.getInt("productID"));
                    int quantity = rs.getInt("quantity");

                    pSold.put(pModel, quantity);
                }
                return pSold;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<ProductsSoldModel> getAllProductsSold() {
        ArrayList<ProductsSoldModel> productssold = new ArrayList<>();
        String sql = "SELECT * FROM productssold";

        try (PreparedStatement stmt = connection.prepareStatement(sql) ;
                ResultSet rs = stmt.executeQuery()) {
            while(rs.next()){
                ProductsSoldModel p = new ProductsSoldModel();
                p.setProductID(rs.getInt("productID"));
                p.setTransactionID(rs.getInt("transactionID"));
                p.setQuantity(rs.getInt("quantity"));
                p.setPrice(rs.getDouble("totalprice"));

                productssold.add(p);
            }

            return productssold;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateProductsSold(ProductsSoldModel productSold) {
        //needs implemented
    }

    @Override
    public void deleteProductsSold(int saleID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProductsSold'");
    }
    
}
