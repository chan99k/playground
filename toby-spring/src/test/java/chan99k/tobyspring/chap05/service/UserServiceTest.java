package chan99k.tobyspring.chap05.service;

import static chan99k.tobyspring.chap05.service.UserService.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import chan99k.tobyspring.chap05.dao.UserDao;
import chan99k.tobyspring.chap05.domain.Level;
import chan99k.tobyspring.chap05.domain.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:chan99k/tobyspring/chap05/test-applicationContext.xml")
@Sql(scripts = "/chan99k/tobyspring/chap05/sql/users_create.sql")
@Sql(scripts = "/chan99k/tobyspring/chap05/sql/users_delete.sql")
class UserServiceTest {
	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	@Autowired
	PlatformTransactionManager transactionManager;

	List<User> users;

	@BeforeEach
	public void setUp() {
		users = Arrays.asList(
			new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 0),
			new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0),
			new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
			new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
			new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
		);
	}

	@Test
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		for (User user : users)
			userDao.add(user);

		userService.upgradeLevels();

		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
	}

	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if (upgraded) {
			assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
		} else {
			assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
		}
	}

	@Test
	public void add() {
		userDao.deleteAll();

		User userWithLevel = users.get(4);      // GOLD 레벨
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);

		userService.add(userWithLevel);
		userService.add(userWithoutLevel);

		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

		assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
		assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
	}

	@Test
	public void upgradeAllOrNothing() throws Exception {
		UserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
		testUserService.setTxManager(transactionManager);

		userDao.deleteAll();
		for (User user : users)
			userDao.add(user);

		try {
			testUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {
		}

		checkLevelUpgraded(users.get(1), false);
	}

	static class TestUserService extends UserService {
		private String id;

		private TestUserService(String id) {
			this.id = id;
		}

		protected void upgradeLevel(User user) {
			if (user.getId().equals(this.id))
				throw new TestUserServiceException();
			super.upgradeLevel(user);
		}
	}

	static class TestUserServiceException extends RuntimeException {
	}

}