package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;


@SpringBootApplication

public class SpringmvcApplication extends WebMvcConfigurerAdapter {

	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringmvcApplication.class, args);
		
	}
	
	/**
	 * 国际化操作
	 * @return
	 */
	@Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
          messageSource.setBasename("messages");
        return messageSource;
    }
	
	/**
	 * 切换中英文 方式一：配置文件中更改lang的值
	 * @return
	 */
/*	@Value("${lang}")
	private String lang;
	@Bean
	public LocaleResolver localeResolver() {
		FixedLocaleResolver slr = new FixedLocaleResolver();
		if(lang.equals("1")) {
			slr.setDefaultLocale(Locale.CHINA);
		}else if(lang.equals("2"))  {
			slr.setDefaultLocale(Locale.US);
		}
		return slr;
	}*/
	
	/**
	 * 切换中英文 方式二： 更改Local
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
			slr.setDefaultLocale(Locale.CHINA);
			//slr.setDefaultLocale(Locale.US);
		return slr;
	}
	
	
	@Autowired
	ThymeleafViewResolver thymeleafViewResolver;
	@Autowired
	Environment environment;
	@Bean
	public LiteDeviceDelegatingViewResolver liteViewResolver() {
		LiteDeviceDelegatingViewResolver resolver = new LiteDeviceDelegatingViewResolver(thymeleafViewResolver);
		RelaxedPropertyResolver env = new RelaxedPropertyResolver((PropertyResolver) environment,"spring.mobile.devicedelegatingviewresolver");
		resolver.setNormalPrefix(env.getProperty("normal-prefix",""));
		resolver.setNormalSuffix(env.getProperty("normal-suffix",""));
		resolver.setMobilePrefix(env.getProperty("mobile-prefix","mobile/"));
		resolver.setMobileSuffix(env.getProperty("mobile-prefix",""));
		resolver.setTabletPrefix(env.getProperty("tablet-prefix","tablet/"));
		resolver.setTabletSuffix(env.getProperty("tablet-prefix",""));
		resolver.setOrder(thymeleafViewResolver.getOrder());
		resolver.setEnableFallback(true);
		return resolver;
	}
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		resolvers.add(liteViewResolver());
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);
		List<View> views = new ArrayList<View>();
		//views.add(mappingJackson2JsonView());
		resolver.setDefaultViews(views);
		return resolver;
	}
}
