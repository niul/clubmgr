package com.niulbird.clubmgr.email;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import freemarker.template.TemplateException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

@SpringBootApplication
@ComponentScan(basePackages = "com.niulbird.clubmgr")
@EnableJpaRepositories(basePackages = "com.niulbird.clubmgr.db.repository")
@EntityScan(basePackages = "com.niulbird.clubmgr.db.model")
public class TestApplication {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:email-messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        when(mailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        return mailSender;
    }

    @Bean(name = "props")
    public Properties props() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("global.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactory() {
        FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactory = new FreeMarkerConfigurationFactoryBean();
        freeMarkerConfigurationFactory.setTemplateLoaderPath("classpath:fmtemplates");
        freeMarkerConfigurationFactory.setPreferFileSystemAccess(false);
        return freeMarkerConfigurationFactory;
    }

    @Bean
    public freemarker.template.Configuration freeMarkerConfiguration(FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactory) throws IOException, TemplateException {
        return freeMarkerConfigurationFactory.createConfiguration();
    }
}
