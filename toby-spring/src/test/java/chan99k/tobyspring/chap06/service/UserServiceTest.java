package chan99k.tobyspring.chap06.service;

import static chan99k.tobyspring.chap05.service.UserService.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import chan99k.tobyspring.chap05.dao.UserDao;
import chan99k.tobyspring.chap05.domain.Level;
import chan99k.tobyspring.chap05.domain.User;
import chan99k.tobyspring.chap05.service.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:chan99k/tobyspring/chap05/test-applicationContext.xml")
@Sql(scripts = "/chan99k/tobyspring/chap05/sql/users_create.sql")
@Sql(scripts = "/chan99k/tobyspring/chap05/sql/users_delete.sql")
class UserServiceTest {
	@Autowired
	chan99k.tobyspring.chap05.service.UserService userService;
	@Autowired
	UserDao userDao;
	@Autowired
	PlatformTransactionManager transactionManager;
	@Autowired
	MailSender mailSender;

	List<User> users;

	@BeforeEach
	public void setUp() {
				users = Arrays.asList(
			new User("bumjin", "박범진", "user1@ksug.org", "p1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 0),
			new User("joytouch", "강명성", "user2@ksug.org", "p2", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0),
			new User("erwins", "신승한", "user3@ksug.org", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
			new User("madnite1", "이상호", "user4@ksug.org", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
			new User("green", "오민규", "user5@ksug.org", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
		);
	}

	@Test
	@DirtiesContext
	public void upgradeLevels() {
		userDao.deleteAll();
		for (User user : users)
			userDao.add(user);

		MockMailSender mockMailSender = new MockMailSender();
		userService.setMailSender(mockMailSender);

		userService.upgradeLevels();

		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);

		List<String> request = mockMailSender.getRequests();
		assertThat(request.size()).isEqualTo(2);

		assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
		assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
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
		chan99k.tobyspring.chap05.service.UserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
		testUserService.setTxManager(transactionManager);
		testUserService.setMailSender(new MockMailSender());

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

	static class MockMailSender implements MailSender {
		private final List<String> requests = new ArrayList<String>();

		public List<String> getRequests() {
			return requests;
		}

		@Override
		public void send(SimpleMailMessage mailMessage) throws MailException {
			assert mailMessage.getTo() != null;
			requests.add(mailMessage.getTo()[0]);
		}

		@Override
		public void send(SimpleMailMessage... simpleMessages) throws MailException {
			for (SimpleMailMessage simpleMessage : simpleMessages) {
				assert simpleMessage.getTo() != null;
				requests.add(simpleMessage.getTo()[0]);
			}
		}

	}
}