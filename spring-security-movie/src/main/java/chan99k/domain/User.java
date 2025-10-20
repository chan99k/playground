package chan99k.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
	private final String userId;
	private final String password;
	private final String otp;
}
