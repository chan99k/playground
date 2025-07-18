package chan99k.tobyspring.chap01.dao;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.jdbc.Sql;

import chan99k.tobyspring.chap01.dao.DaoFactory;
import chan99k.tobyspring.chap01.dao.UserDao;
import chan99k.tobyspring.chap01.domain.User;

@Disabled
@Sql("/schema.sql")
@SpringBootTest(classes = {DaoFactory.class})
class UserDaoTest {

	@Test
	@DisplayName("chap01 UserDao final 테스트")
	void success() throws ClassNotFoundException, SQLException {
		var context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao userDao = context.getBean("userDao", UserDao.class);

		User user = new User();
		user.setId("white-ship");
		user.setName("백기선");
		user.setPassword("married");

		userDao.add(user);
		System.out.println(user.getId() + "등록 성공");

		User foundUser = userDao.get(user.getId());

		System.out.println(foundUser.getName());
		assertThat(foundUser.getName()).isEqualTo(user.getName());

		System.out.println(foundUser.getPassword());
		assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());

		assertThat(foundUser.getId()).isEqualTo(user.getId());
		System.out.println(foundUser.getId() + " 조회 성공");
	}
}