package dev.jclp.demo.product_command.handler;

import dev.jclp.demo.product_command.model.Command;
import dev.jclp.demo.product_command.model.Reply;
import dev.jclp.demo.product_command.model.dto.ProductDto;
import dev.jclp.demo.product_command.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;
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
            String type = command.type() == null ? "" : command.type().toUpperCase();

            Reply<ProductDto> reply;

            switch (type) {
                case "CREATE":
                    LOGGER.info("Received CREATE command");
                    if (command.body() == null) {
                        LOGGER.warn("Received CREATE command with null body");
                        reply = new Reply<>("ERROR", "Command body cannot be null for CREATE operation", null);
                    }
                    LOGGER.info("Received CREATE command for product: {}", command.body());
                    ProductDto createdProduct = productService.create(command.body());
                    LOGGER.info("Product created successfully: {}", createdProduct);
                    reply = new Reply<>("SUCCESS", "Product created successfully", createdProduct);
                    break;
//                case "UPDATE":
//                    LOGGER.info("Received UPDATE command");
//                    // Handle update command
//                    return new Reply<>("ERROR", "UPDATE operation not implemented", null);
//                    break;
//                case "DELETE":
//                    LOGGER.info("Received DELETE command");
//                    LOGGER.info("Received DELETE command for product: {}", command.body());
//                    return new Reply<>("ERROR", "DELETE operation not implemented", null);
//                    break;
                default:
                    LOGGER.error("Unhandled command type: {}", type);
                    reply = new Reply<>("ERROR", "Unhandled command type: " + type, null);
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
