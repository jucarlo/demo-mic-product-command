package dev.jclp.demo.product_command.services;

import dev.jclp.demo.product_command.entities.Product;
import dev.jclp.demo.product_command.model.dto.ProductDto;
import dev.jclp.demo.product_command.model.mapper.Mappers;
import dev.jclp.demo.product_command.repositories.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductDto create(ProductDto productDto) {
        // Implementation of the create method
        Product productNew = productRepository.save(Mappers.toProductEntity(productDto));
        return Mappers.toProductDto(productNew);
    }
}
