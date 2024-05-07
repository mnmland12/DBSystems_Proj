package Services;

import DataModel.*;
import DataAccess.*;

import java.util.ArrayList;

public class InventoryService {
    private InventoryConnect inventoryConnect;

    public InventoryService(InventoryConnect inventoryConnect){
        this.inventoryConnect = inventoryConnect;
    }

    //CRUD
    public void createProduct(InventoryModel inventory){
        inventoryConnect.createInventory(inventory);
    }

    public ArrayList<InventoryModel> getInventoryProductID(int productID){
        return inventoryConnect.getInventoryByProductID(productID);
    }

    public ArrayList<InventoryModel> getInventoryLocationID(int locationID){
        return inventoryConnect.getInventoryByLocationID(locationID);
    }

    public void updateInventory(InventoryModel inventory){
        inventoryConnect.updateInventory(inventory);
    }

    public void deleteInventory(int productID, int locationID){
        inventoryConnect.deleteInventory(productID, locationID);
    }
}
