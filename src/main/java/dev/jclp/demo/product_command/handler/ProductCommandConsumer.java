package dev.jclp.demo.product_command.handler;

import dev.jclp.demo.product_command.model.Command;
import dev.jclp.demo.product_command.model.Reply;
import dev.jclp.demo.product_command.model.ReplyStatus;
import dev.jclp.demo.product_command.model.dto.ProductDto;
import dev.jclp.demo.product_command.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@Configuration
public class ProductCommandConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCommandConsumer.class);

    private final ProductService productService;

    public ProductCommandConsumer(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public Function<Message<Command<ProductDto>>, Message<Reply<?>>> handlerCommands() {



        return msg -> {

            Command<ProductDto> command = msg.getPayload();

            Reply<?> reply;

            switch (command.type()) {
                case CREATE:
                    LOGGER.info("Received CREATE command");
                    if (command.body() == null) {
                        LOGGER.warn("Received CREATE command with null body");
                        reply = new Reply<>(ReplyStatus.ERROR, "Command body cannot be null for CREATE operation", null);
                    } else {
                        LOGGER.info("Received CREATE command for product: {}", command.body());
                        ProductDto createdProduct = productService.create(command.body());
                        LOGGER.info("Product created successfully: {}", createdProduct);
                        reply = new Reply<>(ReplyStatus.SUCCESS, "Product created successfully", createdProduct);
                    }
                    break;
                    case READ:
                    LOGGER.info("Received READ command");
                    if (command.id() == null) {
                        LOGGER.warn("Received READ command with null id");
                        reply = new Reply<>(ReplyStatus.ERROR, "Command id cannot be null for READ operation", null);
                    } else {
                        LOGGER.info("Received READ command for product id: {}", command.id());
                        ProductDto product = productService.findById(command.id());
                        if (product != null) {
                            LOGGER.info("Product read successfully: {}", product);
                            reply = new Reply<>(ReplyStatus.SUCCESS, "Product read successfully", product);
                        } else {
                            LOGGER.warn("Product not found for id: {}", command.id());
                            reply = new Reply<>(ReplyStatus.ERROR, "Product not found for id: " + command.id(), null);
                        }
                    }
                    break;
                    case READ_ALL:
                    LOGGER.info("Received READ_ALL command");
                    java.util.List<ProductDto> products = productService.findAll();
                    LOGGER.info("Products read successfully: {}", products);
                    reply = new Reply<>(ReplyStatus.SUCCESS, "Products read successfully", products);
                    break;

                case UPDATE:
                    LOGGER.info("Received UPDATE command");
                    if (command.id() == null || command.body() == null) {
                        LOGGER.warn("Received UPDATE command with null id or body");
                        reply = new Reply<>(ReplyStatus.ERROR, "Command id and body cannot be null for UPDATE operation", null);
                    } else {
                        LOGGER.info("Received UPDATE command for product id: {} with data: {}", command.id(), command.body());
                        ProductDto updatedProduct = productService.update(command.id(), command.body());
                        LOGGER.info("Product updated successfully: {}", updatedProduct);
                        reply = new Reply<>(ReplyStatus.SUCCESS, "Product updated successfully", updatedProduct);
                    }
                    break;
                case DELETE:
                    LOGGER.info("Received DELETE command");
                    if (command.id() == null) {
                        LOGGER.warn("Received DELETE command with null id");
                        reply = new Reply<>(ReplyStatus.ERROR, "Command id cannot be null for DELETE operation", null);
                    } else {
                        boolean deleted = productService.delete(command.id());
                        if (deleted) {
                            LOGGER.info("Product deleted successfully: {}", command.id());
                            reply = new Reply<>(ReplyStatus.SUCCESS, "Product deleted successfully", null);
                        } else {
                            LOGGER.warn("Product not found for id: {}", command.id());
                            reply = new Reply<>(ReplyStatus.ERROR, "Product not found for id: " + command.id(), null);
                        }
                    }
                    break;
                default:
                    LOGGER.error("Unhandled command type: {}", command.type());
                    reply = new Reply<>(ReplyStatus.ERROR, "Unhandled command type: " + command.type(), null);
            }

            String correlationId = msg.getHeaders().get("correlationId", String.class);
            LOGGER.info("Receiving Correlation ID: {}", correlationId);

            MessageBuilder<Reply<?>> out = MessageBuilder.withPayload(reply);
            if (correlationId != null) {
                out.setHeader("correlationId", correlationId);
            }
            return out.build();
        };
    }
}
