package com.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.version4.chapter15.service.HessianRemoteUserService;

@Configuration
//用于在基于Java类定义Bean配置中开启MVC支持，和XML中的<mvc:annotation-driven>功能一样
@EnableWebMvc
//<context:component-scan base-package="com.version4" />
@ComponentScan("com.version4")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver viewResolver(){
		//配置jsp视图解析器
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultservlethandlerconfigurer){
		//处理静态资源
		/**
		Hessian地址总是抛出了FileNotFoundException也就是404，网上找了好久也不知道是为什么，最终，看了一下tomcat的运行信息
		其中有这么两条
		INFO: Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler]
		INFO: Mapped URL path [/aaa] onto handler 'hessianExportedItemService'
		这才想起来这个DefaultServletHttpRequestHandler是因为配置了configureDefaultServletHandling才有的，是因为我直接从上一章的例子复制过来的，这个配置了之后会把静态资源都不通过controller直接返回给客户端。所以我估计是因为配置了这个导致hessian的mapping工作不正常。
		删除这一段
		 * */
		//defaultservlethandlerconfigurer.enable();
	}

}
