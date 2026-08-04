package dev.jclp.demo.product_command.model;

public record Reply<T>(String status, String message, T body) {
}
