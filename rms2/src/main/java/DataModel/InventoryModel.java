package DataModel;

import java.io.Serializable;

public class InventoryModel implements Serializable {
    private LocationModel location;
    private ProductModel product;
    private int stockQuantity;
    private int reorderLevel;

    public InventoryModel(LocationModel location, ProductModel product, int stockQuantity, int reorderLevel){
        this.location = location;
        this.product = product;
        this.stockQuantity = stockQuantity;
        this.reorderLevel = reorderLevel;
    }

    public InventoryModel(){

    }

    //validation
    public boolean validateTransaction(int productID, int locationID){
        if(LocationModel.validateLocationByID(locationID) || ProductModel.validateProductID(productID))
            return false;
        return true;
    }

    //tostring
    public String toString() {
        return "InventoryModel{" +
                "location=" + location +
                ", product=" + product +
                ", stockQuantity=" + stockQuantity +
                ", reorderLevel=" + reorderLevel +
                '}';
    }

    //getters + setters
    public void setStockQuantity(int stockQuantity){
        this.stockQuantity = stockQuantity;
    }

    public void setReorderLevel(int reorderLevel){
        this.reorderLevel = reorderLevel;
    }

    public void setLocation(LocationModel location){
        this.location = location;
    }

    public void setProduct(ProductModel product){
        this.product = product;
    }

    public LocationModel getLocation(){
        return location;
    }

    public ProductModel getProduct(){
        return product;
    }

    public int getStockQuantity(){
        return stockQuantity;
    }

    public int getReorderLevel(){
        return reorderLevel;
    }
}
