package DataAccess;

import java.util.ArrayList;

import DataModel.ProductsSoldModel;

public interface PSRepository {
    void createProductsSold(ProductsSoldModel productSold);

    ProductsSoldModel getProductsSoldModelByID(int saleID);
    ArrayList<ProductsSoldModel> getAllProductsSold();

    void updateProductsSold(ProductsSoldModel productSold);

    void deleteProductsSold(int saleID);
}
