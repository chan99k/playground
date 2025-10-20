package chan99k.repository.otp;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.entitiy.otp.OtpEntity;

public interface OtpJpaRepository extends JpaRepository<OtpEntity, Integer> {
	Optional<OtpEntity> findOtpEntityByUserId(String userId);
}
