package dev.jclp.demo.product_command.services;

import dev.jclp.demo.product_command.model.dto.ProductDto;


public interface ProductService {

    ProductDto create(ProductDto productDto);
}
