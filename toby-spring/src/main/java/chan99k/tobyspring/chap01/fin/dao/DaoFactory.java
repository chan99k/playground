package chan99k.tobyspring.chap01.fin.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration("chap01DaoFactory")
public class DaoFactory {

	@Bean(name = "chap01DataSource")
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;
	}

	@Bean(name = "chap01UserDao")
	public UserDao userDao() {
		UserDao userDao = new NUserDao();
		// userDao.setDataSource(dataSource());
		return userDao;
	}
}
