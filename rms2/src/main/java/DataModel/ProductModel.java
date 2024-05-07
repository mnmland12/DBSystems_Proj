package DataModel;

import java.io.Serializable;
import java.sql.Connection;

import DataAccess.DatabaseConnection;
import DataAccess.ProductConnect;

public class ProductModel implements Serializable{
    private int productID;
    private String productName;
    private String description = "";
    private double price;
    private String size;

    public ProductModel(int productID, String productName, double price){
        this.productID = productID;
        this.productName = productName;
        this.price = price;
    }

    public ProductModel(int productID, String productName, double price, String description, String size) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.size = size;
    }

    public ProductModel(){

    }

    //toString
    public String toString(){
        return this.productName + " " + this.price + " " + this.size;
    }

    //validate
    public static boolean validateProductID(int productID){
        Connection conn = DatabaseConnection.getInstance();
        ProductConnect product = new ProductConnect(conn);
        if(product.getProductByID(productID) == null)
            return false;
        return true;
    }

    //getters + setters
    public void setProductID(int productID){
        this.productID = productID;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setSize(String size){
        this.size = size;
    }

    public int getProductID(){
        return productID;
    }

    public String getProductName(){
        return productName;
    }

    public String getDescription(){
        return description;
    }

    public double getPrice(){
        return price;
    }

    public String getSize(){
        return size;
    }
}
