package chan99k.tobyspring.chap05.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import chan99k.tobyspring.chap05.domain.Level;
import chan99k.tobyspring.chap05.domain.User;

public class UserDaoJdbc implements UserDao {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private final RowMapper<User> userMapper =
		new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setLevel(Level.valueOf(rs.getInt("level")));
				user.setLogin(rs.getInt("login"));
				user.setRecommend(rs.getInt("recommend"));
				return user;
			}
		};

	public void add(final User user) {
		this.jdbcTemplate.update(
			"insert into users(id, name, email, password, level, login, recommend) values(?,?,?,?,?,?,?)",
			user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getLevel().getValue(),
			user.getLogin(), user.getRecommend());
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
			new Object[] {id}, this.userMapper);
	}

	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}

	public int getCount() {
		Integer count = this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
		return count != null ? count : 0;
	}

	@Override
	public void update(User user) {
		this.jdbcTemplate.update(
			"update users set name = ?, email = ?, password = ?,  level = ?, login = ?, recommend = ? where id = ? ",
			user.getName(), user.getEmail(), user.getPassword(), user.getLevel().getValue(), user.getLogin(),
			user.getRecommend(),
			user.getId()
		);
	}

	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
	}
}