package chan99k.tobyspring.chap05.service;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import chan99k.tobyspring.chap05.dao.UserDao;
import chan99k.tobyspring.chap05.domain.Level;
import chan99k.tobyspring.chap05.domain.User;
import lombok.Setter;

@Setter
public class UserService {
	public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	UserDao userDao;
	PlatformTransactionManager txManager;

	public void upgradeLevels() {

		TransactionStatus status = this.txManager.getTransaction(new DefaultTransactionDefinition());
		try {
			List<User> users = userDao.getAll();
			for (User user : users) {
				if (canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			this.txManager.commit(status);
		} catch (Exception e) {
			this.txManager.rollback(status);
			throw e;
		}
	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		return switch (currentLevel) {
			case BASIC -> (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
			case SILVER -> (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD -> false;
		};
	}

	void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}
