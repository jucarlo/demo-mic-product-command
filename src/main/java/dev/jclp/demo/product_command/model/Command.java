package dev.jclp.demo.product_command.model;

public record Command<T>(String type, Long id, T body) {
}
