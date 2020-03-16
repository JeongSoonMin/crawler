/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.configuration;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import kr.co.smij.framework.resolver.ParamMapHandlerMethodArgumentResolver;

/**
 * @Package : kr.co.smij.framework.configuration
 * @File : WebMvcConfig.java
 * 
 * @Title :
 * @date : 2017. 12. 29.
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "kr.co.smij" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		super.configureMessageConverters(converters);
	}

	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resource/**").addResourceLocations("classpath:/static/resource/"); // 정적 리소스 경로
		registry.addResourceHandler("/image/**").addResourceLocations("file:image/"); // 썸네일 이미지 경로
		super.addResourceHandlers(registry);
	}

//	private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

	/**
	 * @Purpose : 메시징 처리 설정
	 *
	 * @Method Name : messageSource
	 * @Author : 정순민
	 * @Date : 2017. 12. 19.
	 * @Return : MessageSource
	 * @essentialData :
	 * @Description :
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		source.setDefaultEncoding("UTF-8");
		return source;
	}

	/**
	 * @Purpose : 밸리데이터 설정
	 *
	 * @Method Name : getValidator
	 * @Author : 정순민
	 * @Date : 2017. 12. 19.
	 * @essentialData :
	 * @Description :
	 */
	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	/**
	 * @Purpose : interceptor 설정
	 *
	 * @Method Name : addInterceptors
	 * @Author : 정순민
	 * @Date : 2017. 12. 19.
	 * @essentialData :
	 * @Description :
	 */
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new DeviceResolverHandlerInterceptor()); // 디바이스 체크
//		registry.addInterceptor(new BaseInterceptor()).excludePathPatterns("/", "/error/**", "/resource/**",
//				"/favicon.ico", "/*.*", "/login/**", "/regi/**"); // 공통 interceptor
//	}

	/**
	 * @Purpose : 파라미터 처리 설정
	 *
	 * @Method Name : addArgumentResolvers
	 * @Author : 정순민
	 * @Date : 2017. 12. 19.
	 * @essentialData :
	 * @Description :
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new ParamMapHandlerMethodArgumentResolver());
	}

	/**
	 * @Purpose : JSON View 처리
	 *
	 * @Method Name : jsonView
	 * @Author : 정순민
	 * @Date : 2018. 3. 24.
	 * @Return : MappingJackson2JsonView
	 * @essentialData :
	 * @Description :
	 */
	@Bean
	public MappingJackson2JsonView jsonView() {
		return new MappingJackson2JsonView();
	}
}
