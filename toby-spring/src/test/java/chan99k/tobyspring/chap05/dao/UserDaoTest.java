package chan99k.tobyspring.chap05.dao;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import chan99k.tobyspring.chap05.domain.Level;
import chan99k.tobyspring.chap05.domain.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:chan99k/tobyspring/chap05/test-applicationContext.xml")
@Sql(scripts = "/chan99k/tobyspring/chap05/sql/users_create.sql")
@Sql(scripts = "/chan99k/tobyspring/chap05/sql/users_delete.sql")
class UserDaoTest {
	@Autowired
	UserDao dao;
	@Autowired
	DataSource dataSource;

	private User user1;
	private User user2;
	private User user3;

	@BeforeEach
	public void setUp() {
		this.user1 = new User("gyumee", "박성철", "springno1", "user1@ksug.org", Level.BASIC, 1, 0);
		this.user2 = new User("leegw700", "이길원", "springno2", "user2@ksug.org", Level.SILVER, 55, 10);
		this.user3 = new User("bumjin", "박범진", "springno3", "user3@ksug.org", Level.GOLD, 100, 40);
	}

	@Test
	public void andAndGet() {
		dao.deleteAll();
		assertThat(dao.getCount()).isEqualTo(0);

		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount()).isEqualTo(2);

		User userget1 = dao.get(user1.getId());
		checkSameUser(userget1, user1);

		User userget2 = dao.get(user2.getId());
		checkSameUser(userget2, user2);
	}

	@Test
	public void getUserFailure() {
		dao.deleteAll();
		assertThat(dao.getCount()).isEqualTo(0);

		Assertions.assertThatThrownBy(() -> dao.get("unknown_id")).isInstanceOf(EmptyResultDataAccessException.class);
	}

	@Test
	@DisplayName("사용자 정보 수정")
	void update() {
		dao.deleteAll();
		dao.add(user1);
		dao.add(user2); // 수정 하지 않을 사용자

		user1.setName("오민규");
		user1.setPassword("spring06");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);
		dao.update(user1);

		User updated = dao.get(user1.getId());
		checkSameUser(user1, updated);
		User same = dao.get(user2.getId());
		checkSameUser(user2, same);
	}

	@Test
	public void count() {
		dao.deleteAll();
		assertThat(dao.getCount()).isEqualTo(0);

		dao.add(user1);
		assertThat(dao.getCount()).isEqualTo(1);

		dao.add(user2);
		assertThat(dao.getCount()).isEqualTo(2);

		dao.add(user3);
		assertThat(dao.getCount()).isEqualTo(3);
	}

	@Test
	public void getAll() {
		dao.deleteAll();

		List<User> users0 = dao.getAll();
		assertThat(users0.size()).isEqualTo(0);

		dao.add(user1); // Id: gyumee
		List<User> users1 = dao.getAll();
		assertThat(users1.size()).isEqualTo(1);
		checkSameUser(user1, users1.getFirst());

		dao.add(user2); // Id: leegw700
		List<User> users2 = dao.getAll();
		assertThat(users2.size()).isEqualTo(2);
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));

		dao.add(user3); // Id: bumjin
		List<User> users3 = dao.getAll();
		assertThat(users3.size()).isEqualTo(3);
		checkSameUser(user3, users3.get(0));
		checkSameUser(user1, users3.get(1));
		checkSameUser(user2, users3.get(2));
	}

	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId()).isEqualTo(user2.getId());
		assertThat(user1.getName()).isEqualTo(user2.getName());
		assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
		assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
		assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
		assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
	}

	@Test
	public void duplicatedKey() {
		dao.deleteAll();

		dao.add(user1);
		Assertions.assertThatThrownBy(() -> dao.add(user1)).isInstanceOf(DuplicateKeyException.class);
	}

	@Test
	public void sqlExceptionTranslate() {
		dao.deleteAll();

		try {
			dao.add(user1);
			dao.add(user1);
		} catch (DuplicateKeyException ex) {
			SQLException sqlEx = (SQLException)ex.getCause();
			SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
			DataAccessException transEx = set.translate(null, null, sqlEx);
			assertThat(transEx).isInstanceOf(DuplicateKeyException.class);
		}
	}
}