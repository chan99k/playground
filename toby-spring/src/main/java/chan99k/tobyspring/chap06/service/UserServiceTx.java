package chan99k.tobyspring.chap06.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import chan99k.tobyspring.chap06.domain.User;
import lombok.Setter;

@Setter
public class UserServiceTx implements UserService {

	PlatformTransactionManager transactionManager;

	UserService userService;

	@Override
	public void add(User user) {
		userService.add(user);
	}

	@Override
	public void upgradeLevels() {
		TransactionStatus status = this.transactionManager
			.getTransaction(new DefaultTransactionDefinition());

		try {
			userService.upgradeLevels();
			this.transactionManager.commit(status);
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}
}
