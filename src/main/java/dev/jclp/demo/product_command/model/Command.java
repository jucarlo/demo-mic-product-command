package dev.jclp.demo.product_command.model;

public record Command<T>(CommandType type, Long id, T body) {
}
