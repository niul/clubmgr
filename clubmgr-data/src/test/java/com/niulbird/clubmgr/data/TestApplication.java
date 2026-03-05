package com.niulbird.clubmgr.data;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { FreeMarkerAutoConfiguration.class })
@ComponentScan(basePackages = "com.niulbird.clubmgr")
@EnableJpaRepositories(basePackages = "com.niulbird.clubmgr.db.repository")
@EntityScan(basePackages = "com.niulbird.clubmgr.db.model")
public class TestApplication {

    @Bean
    public Properties props() throws IOException {
        return PropertiesLoaderUtils.loadProperties(new ClassPathResource("application-test.properties"));
    }
}
