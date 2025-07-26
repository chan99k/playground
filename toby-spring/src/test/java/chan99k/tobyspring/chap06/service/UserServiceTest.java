package chan99k.tobyspring.chap06.service;

import static chan99k.tobyspring.chap05.service.UserService.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import chan99k.tobyspring.chap06.dao.UserDao;
import chan99k.tobyspring.chap06.domain.Level;
import chan99k.tobyspring.chap06.domain.User;
import lombok.Getter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:chan99k/tobyspring/chap06/test-applicationContext.xml")
@Sql(scripts = "/chan99k/tobyspring/chap05/sql/users_create.sql")
@Sql(scripts = "/chan99k/tobyspring/chap05/sql/users_delete.sql")
class UserServiceTest {
	@Autowired
	ApplicationContext context;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	UserDao userDao;
	@Autowired
	PlatformTransactionManager transactionManager;

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
	public void mockUpgradeLevels() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();

		UserDao mockUserDao = mock(UserDao.class);
		when(mockUserDao.getAll()).thenReturn(this.users);
		userServiceImpl.setUserDao(mockUserDao);

		MailSender mockMailSender = mock(MailSender.class);
		userServiceImpl.setMailSender(mockMailSender);

		userServiceImpl.upgradeLevels();

		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao).update(users.get(1));
		assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
		verify(mockUserDao).update(users.get(3));
		assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);

		ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mockMailSender, times(2)).send(mailMessageArg.capture());
		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
		assertThat(mailMessages.get(0).getTo()[0]).isEqualTo(users.get(1).getEmail());
		assertThat(mailMessages.get(1).getTo()[0]).isEqualTo(users.get(3).getEmail());
	}

	@Test
	public void upgradeLevels() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		MockUserDao mockUserDao = new MockUserDao(this.users);
		userServiceImpl.setUserDao(mockUserDao);

		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);

		userServiceImpl.upgradeLevels();

		List<User> updated = mockUserDao.getUpdated();

		assertThat(updated.size()).isEqualTo(2);
		checkUserAndLevel(updated.get(0), "joytouch", Level.SILVER);
		checkUserAndLevel(updated.get(1), "madnite1", Level.GOLD);

		List<String> request = mockMailSender.getRequests();
		assertThat(request.size()).isEqualTo(2);
		assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
		assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
	}

	private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
		assertThat(updated.getId()).isEqualTo(expectedId);
		assertThat(updated.getLevel()).isEqualTo(expectedLevel);
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

		userServiceImpl.add(userWithLevel);
		userServiceImpl.add(userWithoutLevel);

		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

		assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
		assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
	}

	@Test
	@DirtiesContext // 다이내빅 프록시 팩토리 빈을 직접 만들어 사용할 때는 없앴다가 다시 등장한 컨텍스트 무효화 애너테이션
	public void upgradeAllOrNothing() throws Exception {
		TestUserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(userDao);
		testUserService.setMailSender(new MockMailSender());

		TxProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", TxProxyFactoryBean.class);
		txProxyFactoryBean.setTarget(testUserService);

		UserService txUserService = (UserService) txProxyFactoryBean.getObject();
		userDao.deleteAll();
		for (User user : users) {
			userDao.add(user);
		}

		try {
			txUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException ignored) {

		}

		checkLevelUpgraded(users.get(1), false);
	}

	static class TestUserService extends UserServiceImpl {
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

	@Getter
	static class MockMailSender implements MailSender {
		private final List<String> requests = new ArrayList<String>();

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

	static class MockUserDao implements UserDao {
		private List<User> users;
		private List<User> updated = new ArrayList<>();

		public List<User> getUpdated() {
			return updated;
		}

		private MockUserDao(List<User> users) {
			this.users = users;
		}

		@Override
		public void add(User user) {
			throw new UnsupportedOperationException();
		}

		@Override
		public User get(String id) {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<User> getAll() {
			return this.users;
		}

		@Override
		public void deleteAll() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getCount() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void update(User user) {
			updated.add(user);
		}
	}
}