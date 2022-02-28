package org.kenux.miraclelibrary.global.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LayOutConfig {

    // thymeleaf layout
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
