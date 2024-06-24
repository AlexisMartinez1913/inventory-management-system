package jagarcia.store_inventory_management.service;

import jagarcia.store_inventory_management.entity.Product;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();
    public Product getProductById(Integer id);
    public void save(Product product);
    public void deleteProduct(Product product);

}
