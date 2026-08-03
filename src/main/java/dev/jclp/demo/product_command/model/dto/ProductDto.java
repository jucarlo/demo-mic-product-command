package dev.jclp.demo.product_command.model.dto;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, BigDecimal price) {
}
