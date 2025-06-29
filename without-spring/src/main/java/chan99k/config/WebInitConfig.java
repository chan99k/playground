package chan99k.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 웹 애플리케이션 초기화 클래스
 * DispatcherServlet 및 애플리케이션 컨텍스트 설정
 */
public class WebInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
	/**
	 * 루트 애플리케이션 컨텍스트 설정 클래스 지정
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[0];
	}

	/**
	 * 서블릿 애플리케이션 컨텍스트 설정 클래스 지정
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebConfig.class, SwaggerConfig.class};
	}

	/**
	 * DispatcherServlet 매핑 경로 지정
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
}
