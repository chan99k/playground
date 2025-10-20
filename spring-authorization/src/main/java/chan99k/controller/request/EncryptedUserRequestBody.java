package chan99k.controller.request;

import java.beans.ConstructorProperties;

import chan99k.annotation.PasswordEncryption;
import lombok.Getter;

@Getter
public class EncryptedUserRequestBody {
	private final String userId;

	@PasswordEncryption
	private String password;

	@ConstructorProperties({"userId", "password"})
	public EncryptedUserRequestBody(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
}
