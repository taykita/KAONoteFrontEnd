package ru.kao.kaonotefrontend.configuration.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

//@EnableWebMvc
//@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Bean
    public ITemplateResolver templateResolver() {
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");

        return templateResolver;
    }

//    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

//    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, MessageSource messageSource) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setTemplateEngineMessageSource(messageSource);
        return templateEngine;
    }

//    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(ISpringTemplateEngine templateEngine) {
        ThymeleafViewResolver bean = new ThymeleafViewResolver();
        bean.setTemplateEngine(templateEngine);
        bean.setOrder(1);
        return bean;
    }
}
