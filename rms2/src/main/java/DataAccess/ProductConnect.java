package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataModel.ProductModel;

public class ProductConnect implements ProductRepository {
    private Connection connection;

    public ProductConnect(Connection connection){
        this.connection = connection;
    }

    public ArrayList<ProductModel> getProductWSize(String name, String size) {
        String sql = "SELECT * FROM product WHERE productname LIKE CONCAT('%', ?, '%') AND size = ?";

        ArrayList<ProductModel> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, size);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductModel product = new ProductModel();
                    product.setProductID(rs.getInt("productID"));
                    product.setProductName(rs.getString("productname"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setSize(rs.getString("size"));

                    result.add(product);
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    public ArrayList<ProductModel> getProductBySize(String size) {
        String sql = "SELECT * FROM product WHERE size = ?";

        ArrayList<ProductModel> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, size);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductModel product = new ProductModel();
                    product.setProductID(rs.getInt("productID"));
                    product.setProductName(rs.getString("productname"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setSize(rs.getString("size"));

                    result.add(product);
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getProductID(ProductModel productModel){
        String sql = "SELECT productID FROM product WHERE productname = ?"+
                    " AND price = ? AND size = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, productModel.getProductName());
            stmt.setDouble(2, productModel.getPrice());
            stmt.setString(3, productModel.getSize());
            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return 0;
                }
                int pID = rs.getInt("productID");
                return pID;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }

    }

    public ArrayList<ProductModel> getProductByName(String name){
        String sql = "SELECT * FROM product WHERE productname LIKE CONCAT('%', ?, '%')";

        ArrayList<ProductModel> result = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, name);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    ProductModel product = new ProductModel();
                    product.setProductID(rs.getInt("productID"));
                    product.setProductName(rs.getString("productname"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setSize(rs.getString("size"));

                    result.add(product);
                }
                return result;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int createProduct(ProductModel product) {
        String pn = product.getProductName();
        String d = product.getDescription();
        double p = product.getPrice();
        String size = product.getSize();

        String sql = "INSERT INTO product (productname, description, price, size)"+
                    " VALUES (?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, pn);
            stmt.setString(2, d);
            stmt.setDouble(3, p);
            stmt.setString(4, size);

            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("Product creation successful.");

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                }else{
                    System.out.println("No product ID generated.");
                    return -1;
                }
            }else{
                System.out.println("Product Creation unsuccessful.");
                return -1;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public ProductModel getProductByID(int productID) {
        String sql = "SELECT * FROM product WHERE productID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, productID);
            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return null;
                }
                ProductModel product = new ProductModel();
                product.setProductID(productID);
                product.setProductName(rs.getString("productname"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setSize(rs.getString("size"));
                return product;
            }

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<ProductModel> getAllProducts() {
        ArrayList<ProductModel> products = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try(PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                ProductModel product = new ProductModel();
                product.setProductName(rs.getString("productname"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setSize(rs.getString("size"));
                
                products.add(product);
            }
            return products;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateProductPrice(ProductModel product) {
        String sql = "UPDATE product SET price = ? WHERE productID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDouble(1, product.getPrice());
            stmt.setInt(2, product.getProductID());
            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("Product Update successful.");
            }else{
                System.out.println("Product Update unsuccessful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int productID) {
        String sql = "DELETE FROM product WHERE productID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, productID);
            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("Product deletion successful.");
            }else{
                System.out.println("Product deletion unsuccessful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
