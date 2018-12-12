package com.config;

import java.util.Properties;

import javax.jws.WebService;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.version4.chapter15.service.BurlapRemoteUserService;
import com.version4.chapter15.service.HessianRemoteUserService;
import com.version4.chapter15.service.HttpInvokerRemoteUserService;
import com.version4.chapter15.service.RemoteUserService;

@Configuration
//只是用了@ComponentScan，这样就可以在项目中用非web的组件来充实完善RootConfig
@ComponentScan(basePackages = {"com.version4"}, excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
@ImportResource({"classpath:config/jms/jms-server.xml", "classpath:config/jms/jms-sub.xml"})
public class RootConfig {

	//注册服务器端的rmi bean
	//rmi使用任意端口交互，这是防火墙不允许的，在企业内部通常不用关心这个问题，但是在互联网环境中可能会遇到问题
	//而且rmi是基于java的，这意味着客户端和服务器端都需要用java开发，网络传输的对象类型必须要保证在调用两端的java运行时是完全相同的版本
	@Bean
	public RmiServiceExporter rmiExporter(RemoteUserService remoteUserService){
		RmiServiceExporter rmiExporter = new RmiServiceExporter();
		rmiExporter.setService(remoteUserService);
		rmiExporter.setServiceName("RemoteUserService");
		rmiExporter.setServiceInterface(RemoteUserService.class);
		rmiExporter.setRegistryPort(1100);
		//rmiExporter.setRegistryHost("rmi.user.com");
		return rmiExporter;
	}

	//hessian是基于http请求的，所以hessianExporter实现为一个spring mvc控制器
	@Bean
	public HessianServiceExporter hessianExporter(HessianRemoteUserService hessianRemoteUserService){
		HessianServiceExporter hessianExporter = new HessianServiceExporter();
		hessianExporter.setService(hessianRemoteUserService);
		hessianExporter.setServiceInterface(HessianRemoteUserService.class);
		return hessianExporter;
	}

	@Bean
	public HandlerMapping hessianMapping(){
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		Properties mappings = new Properties();
		mappings.setProperty("/hessianRemoteUserService.service", "hessianExporter");
		mapping.setMappings(mappings);
		return mapping;
	}

	@Bean
	public BurlapServiceExporter burlapExporter(BurlapRemoteUserService burlapRemoteUserService){
		BurlapServiceExporter burlapExporter = new BurlapServiceExporter();
		burlapExporter.setService(burlapRemoteUserService);
		burlapExporter.setServiceInterface(BurlapRemoteUserService.class);
		return burlapExporter;
	}

	@Bean
	public HandlerMapping burlapMapping(){
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		Properties mappings = new Properties();
		mappings.setProperty("/burlapRemoteUserService.service", "burlapExporter");
		mapping.setMappings(mappings);
		return mapping;
	}

	//是rmi和hessian的结合，有这两个框架的优势，但只能用于spring框架，用于java
	@Bean
	public HttpInvokerServiceExporter httpExportedService(HttpInvokerRemoteUserService service) {
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setService(service);
		exporter.setServiceInterface(HttpInvokerRemoteUserService.class);
		return exporter;
	}

	@Bean
	public HandlerMapping httpInvokerMapping() {
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		Properties mappings = new Properties();
		mappings.setProperty("/httpInvokerRemoteUserService.service", "httpExportedService");
		mapping.setMappings(mappings);
		return mapping;
	}

	//当对象的生命周期不是由spring管理的，而对象的属性又需要注入spring所管理的bean时，该对象需要继承SpringBeanAutowiringSupport
	//SimpleJaxWsServiceExporter不需要为它指定一个被导出bean的引用，它会将使用@WebService注解的所有bean发布为JAX-WS服务
	//由于@WebService(serviceName="JaxWsService")，所以这个bean所形成的web服务地址为http://localhost:8888/services/JaxWsService
	@Bean
	public SimpleJaxWsServiceExporter jaxWsExporter() {
		SimpleJaxWsServiceExporter exporter = new SimpleJaxWsServiceExporter();
		exporter.setBaseAddress("http://localhost:8888/services/");
		return exporter;
	}

}
