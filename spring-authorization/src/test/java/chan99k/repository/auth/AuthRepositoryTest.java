package chan99k.repository.auth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionOperations;

import chan99k.domain.user.User;
import chan99k.entitiy.otp.OtpEntity;
import chan99k.entitiy.user.UserEntity;
import chan99k.repository.otp.OtpJpaRepository;
import chan99k.repository.user.UserJpaRepository;
import chan99k.util.OtpCodeUtil;

@ExtendWith(MockitoExtension.class)
class AuthRepositoryTest {
	@Mock
	OtpJpaRepository otpJpaRepository;

	@Mock
	UserJpaRepository userJpaRepository;

	AuthRepository sut;

	String userId = "danny.kim";
	String password = "1q2w3e4r";

	@BeforeEach
	public void setUp() {
		sut = new AuthRepository(
			otpJpaRepository,
			userJpaRepository,
			TransactionOperations.withoutTransaction(),
			TransactionOperations.withoutTransaction()
		);
	}

	@Test
	@DisplayName("동일한 사용자 아이디로 사용자를 등록할 수 없다")
	void test01() {
		UserEntity userEntity = new UserEntity(userId, password);

		given(userJpaRepository.findUserEntityByUserId(userId))
			.willReturn(Optional.of(userEntity));

		assertThatThrownBy(() -> sut.createNewUser(userEntity.toDomain())).isInstanceOf(RuntimeException.class);
	}

	@Test
	@DisplayName("유니크한 사용자 아이디로 사용자를 등록할 수 있다")
	void test02() {
		UserEntity userEntity = new UserEntity(userId, password);

		given(userJpaRepository.findUserEntityByUserId(userId))
			.willReturn(Optional.empty());

		User user = new User(userId, password);
		given(userJpaRepository.save(any())).willReturn(user.toEntity());

		User result = sut.createNewUser(user);

		verify(userJpaRepository, atMostOnce()).save(user.toEntity());
		assertThat(result.getUserId()).isEqualTo(userId);
		assertThat(result.getPassword()).isEqualTo(password);
	}

	@Test
	@DisplayName("사용자가 존재하면 OTP 값을 업데이트한다")
	void test03() {
		String otp = OtpCodeUtil.generateOtpCode();
		OtpEntity otpEntity = new OtpEntity();
		given(otpJpaRepository.findOtpEntityByUserId(userId))
			.willReturn(Optional.of(otpEntity));

		sut.upsertOtp(userId, otp);

		Assertions.assertEquals(otp, otpEntity.getOtpCode());
	}

	@Test
	@DisplayName("사용자가 존재하지 않으면 새로운 OTP를 생성한다")
	void test04() {
		String otp = OtpCodeUtil.generateOtpCode();
		OtpEntity otpEntity = new OtpEntity();
		given(otpJpaRepository.findOtpEntityByUserId(userId))
			.willReturn(Optional.empty());

		sut.upsertOtp(userId, otp);

		verify(otpJpaRepository, atMostOnce()).save(any());
	}
}