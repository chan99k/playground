package chan99k.observer.spring;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private final ApplicationEventPublisher eventPublisher;

	public UserService(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Transactional
	public void registerUser(String email, String password) {
		System.out.println(email + " 사용자 정보 DB 저장 시도...");
		// DB 저장 로직 ...
		System.out.println("DB 저장 성공.");

		// 트랜잭션이 커밋된 후 이벤트가 발행되도록 예약
		eventPublisher.publishEvent(new UserRegistrationEvent(email));
	}
}
