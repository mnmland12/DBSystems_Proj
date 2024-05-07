package DataAccess;

import java.util.ArrayList;

import DataModel.ProductModel;

public interface ProductRepository {
    int createProduct(ProductModel product);

    ProductModel getProductByID(int productID);
    ArrayList<ProductModel> getAllProducts();

    void updateProductPrice(ProductModel product);

    void deleteProduct(int productID);
}
