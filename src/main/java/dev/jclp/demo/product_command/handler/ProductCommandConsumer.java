package dev.jclp.demo.product_command.handler;

import dev.jclp.demo.product_command.model.Command;
import dev.jclp.demo.product_command.model.dto.ProductDto;
import dev.jclp.demo.product_command.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductCommandConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCommandConsumer.class);

    private final ProductService productService;

    public ProductCommandConsumer(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public Consumer<Command<ProductDto>> handlerCommands() {

        return command -> {
            String type = command.type() == null ? "" : command.type().toUpperCase();

            switch (type) {
                case "CREATE":
                    LOGGER.info("Received CREATE command");
                    if (command.body() == null) {
                        LOGGER.warn("Received CREATE command with null body");
                        return;
                    }
                    LOGGER.info("Received CREATE command for product: {}", command.body());
                    ProductDto createdProduct = productService.create(command.body());
                    LOGGER.info("Product created successfully: {}", createdProduct);
                    break;
                case "UPDATE":
                    LOGGER.info("Received UPDATE command");
                    // Handle update command
                    break;
                case "DELETE":
                    LOGGER.info("Received DELETE command");
                    LOGGER.info("Received DELETE command for product: {}", command.body());
                    break;
                default:
                    LOGGER.warn("Unhandled command type: {}", type);
            }
        };
    }
}
