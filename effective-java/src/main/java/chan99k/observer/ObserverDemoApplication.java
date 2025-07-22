package chan99k.observer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import chan99k.observer.spring.UserService;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"chan99k.observer.spring"})
public class ObserverDemoApplication implements CommandLineRunner {
	private final UserService userService;

	public ObserverDemoApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ObserverDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("--- 사용자 가입 알림 기능 테스트 ---");
		userService.registerUser("test@test.com", "1234");
		System.out.println("--- UserService.registerUser() 메소드 종료 ");
		userService.registerUser("test02@test.com", "123456");
		System.out.println("--- UserService.registerUser() 메소드 종료 ");
	}
}
