package chan99k.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class OtpCodeUtil {
	public static String generateOtpCode() {
		try {
			SecureRandom secureRandom = SecureRandom.getInstanceStrong();
			int randomOtp = secureRandom.nextInt(900_000) + 100_000;
			return String.valueOf(randomOtp);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
