package chan99k.tobyspring.chap06.dao;

import java.util.List;

import chan99k.tobyspring.chap06.domain.User;

public interface UserDao {

	void add(User user);

	User get(String id);

	List<User> getAll();

	void deleteAll();

	int getCount();

	void update(User user);

}
