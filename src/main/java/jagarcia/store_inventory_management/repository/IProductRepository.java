package jagarcia.store_inventory_management.repository;

import jagarcia.store_inventory_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Integer> {
}
