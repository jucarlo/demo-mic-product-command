package dev.jclp.demo.product_command.services;

import dev.jclp.demo.product_command.model.dto.ProductDto;

import java.util.List;


public interface ProductService {

    ProductDto create(ProductDto productDto);

    ProductDto findById(Long id);

    List<ProductDto> findAll();

    ProductDto update(Long id, ProductDto productDto);

    boolean delete(Long id);
}
