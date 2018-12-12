package com.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**在servlet3.0环境中，容器会在类路径里查找实现了javax.servlet.ServletContainerInitializer接口的类，
 * 如果能够发现，就会用它来配置servlet容器
 * spring提供了这个接口的实现，名为SpringServletContainerIniltializer，这个类反过来又会查找实现了WebApplicationInitializer
 * 的类并将配置的任务交给它们来完成，spring3.2引入了一个便利的WebApplicationInitializer的基础实现，
 * 也就是AbstractAnnotationConfigDispatcherServletInitializer，我们的DemoWebAppInitializer扩展了AbstractAnnotationConfigDispatcherServletInitializer，
 * 因此当部署到servlet3.0容器的时候，容器会自动发现它，并用它来配置servlet上下文。
 * @author dell
 *
 */
public class DemoWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
	 * 返回的类将会用来定义ContextLoaderListener应用上下文中的bean
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{RootConfig.class};
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
	 * 返回的类将会用来定义DispatcherServlet应用上下文中的bean
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		//将DispatcherServlet映射到"/"
		//*.service处理hessian请求
		return new String[]{"/", "*.service"};
	}

}
