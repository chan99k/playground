package chan99k.tobyspring.chap01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chan99k.tobyspring.chap01.domain.User;
import lombok.Setter;

@Setter
public abstract class UserDao {

	public void add(User user) throws ClassNotFoundException, SQLException {
		var conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(
			"insert into users(id,name,password) values (?,?,?)"
		);
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(
			"select * from users where id = ?"
		);
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		conn.close();

		return user;
	}

	/**
	 * 커넥션을 맻는다 라는 관심사가 다른 기능을 별도의 메서드로 추출한다.
	 * 더하여, 기본적인 "템플릿"을 제공하며 상속을 통해 원하는 대로 확장이 가능하도록 열어준다.
	 * @return Connection
	 */
	protected abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}