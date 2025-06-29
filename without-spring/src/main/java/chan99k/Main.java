package chan99k;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import chan99k.config.SwaggerConfig;
import chan99k.config.WebConfig;


public class Main {

	public static void main(String[] args) {
		// 포트 설정  - 8080 대신 8081 사용
		int port = 8081;
		// 포트 사용 가능 여부 확인 - 이미 사용 중인 포트인지 확인
		checkPortAvailability(port);

		// 명시적 커넥터 생성 - 네트워크 바인딩 문제 해결을 위한 설정
		Connector connector = new Connector();
		connector.setPort(port);
		// 모든 네트워크 인터페이스(0.0.0.0)에 바인딩 - 외부 접속 허용
		connector.setProperty("address", "0.0.0.0");

		// 톰캣 인스턴스 생성 및 기본 설정
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(port);
		tomcat.getService().addConnector(connector);
		tomcat.setHostname("localhost");

		// 기본 디렉토리 설정 - 임시 파일 저장 위치
		tomcat.setBaseDir("without-spring/tomcat-temp");

		try {
			// 현재 작업 디렉토리 출력 - 디버깅 목적
			System.out.println("현재 작업 디렉토리: " + new File(".").getAbsolutePath());

			// 웹앱 디렉토리 경로 확인 - 웹 애플리케이션 루트 디렉토리
			String webAppDirectory = "without-spring/src/main/webapp/";
			File webAppDir = new File(webAppDirectory);
			if (!webAppDir.exists()) {
				System.err.println("웹앱 디렉토리가 존재하지 않습니다: " + webAppDir.getAbsolutePath());
				// 디렉토리 생성 시도 - 없는 경우 자동 생성
				if (webAppDir.mkdirs()) {
					System.out.println("웹앱 디렉토리를 생성했습니다: " + webAppDir.getAbsolutePath());
				} else {
					System.err.println("웹앱 디렉토리 생성에 실패했습니다.");
				}
			}

			// WEB-INF 디렉토리 확인 - 웹 애플리케이션 설정 및 리소스 디렉토리
			File webInfDir = new File(webAppDir, "WEB-INF");
			if (!webInfDir.exists()) {
				if (webInfDir.mkdirs()) {
					System.out.println("WEB-INF 디렉토리를 생성했습니다: " + webInfDir.getAbsolutePath());
				}
			}

			// 컨텍스트 생성 - 웹 애플리케이션 컨텍스트 설정
			Context context = tomcat.addContext("", webAppDir.getAbsolutePath());
			System.out.println("웹앱 컨텍스트 추가: " + webAppDir.getAbsolutePath());

			// 클래스 파일 디렉토리 확인 - 컴파일된 클래스 파일 위치
			File additionWebInfClasses = new File("without-spring/build/classes/java/main");
			if (!additionWebInfClasses.exists()) {
				System.err.println("클래스 디렉토리가 존재하지 않습니다: " + additionWebInfClasses.getAbsolutePath());
				System.err.println("프로젝트를 먼저 빌드해주세요: ./gradlew :without-spring:compileJava");
				return;
			}

			// 웹 리소스 설정 - 클래스 파일을 웹 애플리케이션에서 접근 가능하도록 설정
			WebResourceRoot resources = new StandardRoot(context);
			resources.addPreResources(
				new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
			context.setResources(resources);

			// Spring 애플리케이션 컨텍스트 설정 - 자바 설정 클래스 기반 Spring 컨텍스트 초기화
			AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
			applicationContext.register(WebConfig.class, SwaggerConfig.class);

			// Spring DispatcherServlet 설정 및 등록 - Spring MVC의 핵심 서블릿
			DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);

			// Tomcat에 DispatcherServlet 등록 - 모든 웹 요청을 Spring이 처리하도록 설정
			String servletName = "dispatcher";
			tomcat.addServlet("", servletName, dispatcherServlet);
			context.addServletMappingDecoded("/", servletName);

			System.out.println("Spring DispatcherServlet이 '/' 경로에 등록되었습니다.");

			// 디버그 정보 출력 - 서버 시작 전 상태 확인
			System.out.println("Tomcat 시작 전 상태 확인:");
			System.out.println("- 호스트 이름: " + tomcat.getHost().getName());
			System.out.println("- 포트: " + connector.getPort());
			System.out.println("- 주소: " + connector.getProperty("address"));
			System.out.println("- 베이스 디렉토리: " + tomcat.getServer().getCatalinaBase().getAbsolutePath());

			// 톰캣 서버 시작
			tomcat.start();

			// 시작 후 상태 확인 - 서버 실행 상태 로깅
			System.out.println("Tomcat 시작 완료! 상태: " + tomcat.getServer().getState());
			System.out.println("서비스 상태: " + tomcat.getService().getState());
			System.out.println("커넥터 상태: " + connector.getState());
			System.out.println("접속 정보:");
			System.out.println("- http://localhost:" + port + "/ 에서 접속 가능합니다.");
			System.out.println("- Swagger UI: http://localhost:" + port + "/swagger-ui.html");
			System.out.println("- API 엔드포인트: http://localhost:" + port + "/api/hello");

			// 서버 실행 유지 - 메인 스레드 블로킹
			tomcat.getServer().await();

		} catch (LifecycleException e) {
			// 톰캣 라이프사이클 관련 예외 처리 - 서버 시작/중지 과정에서 발생하는 예외
			System.err.println("Tomcat 라이프사이클 예외 발생: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// 기타 예외 처리 - 예상치 못한 오류 발생 시 처리
			System.err.println("애플리케이션 시작 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 지정된 포트가 사용 가능한지 확인하는 메서드
	 * 포트가 이미 사용 중이면 애플리케이션을 종료합니다.
	 * 
	 * @param port 확인할 포트 번호
	 */
	private static void checkPortAvailability(int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			// 포트가 사용 가능하면 소켓이 생성됨 - 테스트 후 자동으로 소켓 닫힘
			System.out.println("포트 " + port + "는 사용 가능합니다.");
		} catch (IOException e) {
			// 포트가 이미 사용 중이면 오류 메시지 출력 후 종료
			System.err.println("포트 " + port + "는 이미 사용 중입니다. 다른 포트를 사용하거나 해당 포트를 사용 중인 프로세스를 종료하세요.");
			System.err.println("오류 메시지: " + e.getMessage());
			System.exit(1);
		}
	}
}
