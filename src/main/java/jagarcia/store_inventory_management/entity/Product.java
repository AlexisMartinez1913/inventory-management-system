package jagarcia.store_inventory_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idProduct;
    String nameProduct;
    String description;
    Double price;
    Integer stock;
}
