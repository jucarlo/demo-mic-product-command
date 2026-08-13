package dev.jclp.demo.product_command.model;

public record Reply<T>(ReplyStatus status, String message, T body) {
}
