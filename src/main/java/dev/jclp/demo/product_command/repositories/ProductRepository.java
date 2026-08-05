package dev.jclp.demo.product_command.repositories;

import dev.jclp.demo.product_command.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}