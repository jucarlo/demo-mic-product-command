package dev.jclp.demo.product_command.repositories;

import dev.jclp.demo.product_command.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}