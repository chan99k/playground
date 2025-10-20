package chan99k.service;

import org.springframework.stereotype.Service;

import chan99k.domain.user.User;
import chan99k.repository.auth.AuthRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final OtpService otpService;
	private final AuthRepository authRepository;
	private final EncryptService encryptService;

	public User createnewUser(String userId, String password) {
		return authRepository.createNewUser(new User(userId, password));
	}

	public String auth(String userId, String password) {
		User user = authRepository.getUserByUserId(userId);

		if (encryptService.matches(user.getPassword(), password)) {
			return otpService.renewOtp(userId);
		}
		throw new RuntimeException();
	}
}
