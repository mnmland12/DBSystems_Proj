package DataAccess;

import java.util.ArrayList;

import DataModel.InventoryModel;

public interface InventoryRepository {
    void createInventory(InventoryModel inventory);

    ArrayList<InventoryModel> getInventoryByProductID(int productID);
    ArrayList<InventoryModel> getInventoryByLocationID(int locationID);

    void updateInventory(InventoryModel inventory);

    void deleteInventory(int productID, int locationID);
}
