package dev.jclp.demo.product_command.model.mapper;

import dev.jclp.demo.product_command.entities.Product;
import dev.jclp.demo.product_command.model.dto.ProductDto;

public final class Mappers {

    private Mappers() {}

    static public ProductDto toProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }

    static public Product toProductEntity(ProductDto productDto) {
        Product product = new Product(productDto.name(), productDto.price());
        product.setId(productDto.id());
        return product;
    }
}
