package chan99k.repository.auth;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionOperations;

import chan99k.domain.user.User;
import chan99k.entitiy.otp.OtpEntity;
import chan99k.entitiy.user.UserEntity;
import chan99k.exception.InvalidAuthException;
import chan99k.repository.otp.OtpJpaRepository;
import chan99k.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthRepository {
	private final OtpJpaRepository otpJpaRepository;
	private final UserJpaRepository userJpaRepository;

	private final TransactionOperations readTransactionOperations;
	private final TransactionOperations writeTransactionOperations;

	public User createNewUser(User user) {
		return writeTransactionOperations.execute(status -> {
			Optional<UserEntity> userOptional = userJpaRepository.findUserEntityByUserId(user.getUserId());
			if (userOptional.isPresent()) {
				throw new RuntimeException(String.format("User [%s] already exists", user.getUserId()));
			}

			UserEntity saved = userJpaRepository.save(user.toEntity());
			return saved.toDomain();
		});
	}

	public User getUserByUserId(String userId) {
		return readTransactionOperations.execute(status ->
			userJpaRepository.findUserEntityByUserId(userId)
				.orElseThrow(InvalidAuthException::new)
				.toDomain());
	}

	public String getOtp(String userId) {
		return readTransactionOperations.execute(status -> otpJpaRepository.findOtpEntityByUserId(userId)
			.orElseThrow(() -> new RuntimeException(String.format("User [%s] does not exist", userId)))
			.getOtpCode());
	}

	public void upsertOtp(String userId, String newOtp) {
		writeTransactionOperations.executeWithoutResult(status -> {
			Optional<OtpEntity> optOptional = otpJpaRepository.findOtpEntityByUserId(userId);

			if (optOptional.isPresent()) {
				optOptional.get().renewOtp(newOtp);
			} else {
				otpJpaRepository.save(new OtpEntity(userId, newOtp));
			}
		});
	}
}
