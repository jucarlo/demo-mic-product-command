package dev.jclp.demo.product_command.handler;

import dev.jclp.demo.product_command.model.Command;
import dev.jclp.demo.product_command.model.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductCommandConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCommandConsumer.class);

    @Bean
    public Consumer<Command<ProductDto>> handlerCommands() {
        return command -> {
            LOGGER.info("Received command: type={} body={}", command.type(), command.body());
        };
    }
}
