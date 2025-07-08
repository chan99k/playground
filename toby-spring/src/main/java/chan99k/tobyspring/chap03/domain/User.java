package chan99k.tobyspring.chap03.domain;

import lombok.Data;

@Data
public class User {
	String id;
	String name;
	String password;

	public User() {
	}

	public User(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
}
