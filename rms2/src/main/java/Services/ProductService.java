package Services;

import DataAccess.*;
import DataModel.*;

public class ProductService {
    private ProductConnect productConnect;

    public ProductService(ProductConnect productConnect){
        this.productConnect = productConnect;
    }


    //CRUD
    public void createProduct(ProductModel product){
        productConnect.createProduct(product);
    }

    public ProductModel getProductByID(int productID){
        return productConnect.getProductByID(productID);
    }

    public void updateProduct(ProductModel product){
        productConnect.updateProductPrice(product);
    }

    public void deleteProduct(int productID){
        productConnect.deleteProduct(productID);
    }
}
