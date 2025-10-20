package chan99k.service;

import org.springframework.stereotype.Service;

import chan99k.repository.auth.AuthRepository;
import chan99k.util.OtpCodeUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {
	private final AuthRepository authRepository;

	public boolean checkOtp(String userId, String sourceOtp) {
		String targetOtp = authRepository.getOtp(userId);
		return targetOtp.equals(sourceOtp);
	}

	public String renewOtp(String userId) {
		String newOtp = OtpCodeUtil.generateOtpCode();
		authRepository.upsertOtp(userId, newOtp);
		return newOtp;
	}

}
