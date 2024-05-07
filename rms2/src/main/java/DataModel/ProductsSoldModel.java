package DataModel;

import java.io.Serializable;

public class ProductsSoldModel implements Serializable{
    private int saleID;
    private int transactionID;
    private int productID;
    private int quantity;
    private double totalPrice;

    public ProductsSoldModel(){

    }

    public ProductsSoldModel(int transactionID, int productID, int quantity, double totalPrice){
        this.transactionID = transactionID;
        this.productID = productID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    //getters + setters
    public int getSaleID(){
        return this.saleID;
    }

    public int getTransactionID(){
        return this.transactionID;
    }

    public int getProductID(){
        return this.productID;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getTotalPrice(){
        return this.totalPrice;
    }

    public void setSaleID(int saleID){
        this.saleID = saleID;
    }

    public void setTransactionID(int transactionID){
        this.transactionID = transactionID;
    }

    public void setProductID(int productID){
        this.productID = productID;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setPrice(double price){
        this.totalPrice = price;
    }
}
