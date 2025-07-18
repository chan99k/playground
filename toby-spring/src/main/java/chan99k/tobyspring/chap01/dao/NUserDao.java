package chan99k.tobyspring.chap01.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao extends UserDao{

	/**
	 * 슈퍼클래스에 명시한 기본적인 로직의 흐름을 구현해서 사용한다. - 템플릿 메소드 패턴
	 */
	@Override
	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager
			.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL", "sa", "");
		return conn;
	}
}
