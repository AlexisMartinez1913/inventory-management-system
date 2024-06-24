package jagarcia.store_inventory_management.service.impl;

import jagarcia.store_inventory_management.entity.Product;
import jagarcia.store_inventory_management.repository.IProductRepository;
import jagarcia.store_inventory_management.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository iProductRepository;
    @Override
    public List<Product> getAllProducts() {
        return iProductRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        Product product = iProductRepository.findById(id).orElse(null);
        return product;
    }

    @Override
    public void save(Product product) {
        iProductRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        iProductRepository.delete(product);
    }
}
