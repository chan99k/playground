package chan99k.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UserRegistrationListeners {
	@EventListener
	@Order(1)
	public void handleUserRegistrationSync(UserRegistrationEvent event) {
		System.out.println("[동기 리스너] " + event.userEmail() + " 님, 가입을 환영합니다!!");
	}

	@Async
	@EventListener
	public void sendWelcomeEmailAsync(UserRegistrationEvent event) throws InterruptedException {
		Thread.sleep(3000);
		System.out.println("[비동기 리스너] 가입 환영 이메일 발송 완료: " + event.userEmail());
	}

	@Order(2)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleUserRegistrationAfterCommit(UserRegistrationEvent event) {
		System.out.println("[트랜잭션 리스너] " + " (커밋 완료 후 실행) 사용자 가입 활동 로그 기록: " + event.userEmail() + ")");
	}
}
