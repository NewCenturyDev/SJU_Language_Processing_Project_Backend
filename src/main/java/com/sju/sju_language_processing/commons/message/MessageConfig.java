package com.sju.sju_language_processing.commons.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localResolver = new SessionLocaleResolver();
        localResolver.setDefaultLocale(Locale.US);
        return localResolver;
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(getValidationMsgSrc());
        return bean;
    }

    @Bean
    public static MessageSource getValidationMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/users/validation",
                "messages/sentences/validation",
                "messages/musics/validation"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getResponseMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/users/response",
                "messages/sentences/response",
                "messages/musics/response"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getStorageMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/storage/validation",
                "messages/storage/response",
                "messages/storage/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getUserMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/users/validation",
                "messages/users/response",
                "messages/users/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getSentenceMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/sentences/validation",
                "messages/sentences/response",
                "messages/sentences/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getMusicMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/musics/validation",
                "messages/musics/response",
                "messages/musics/error"
        );
        return msgSrc;
    }
}
