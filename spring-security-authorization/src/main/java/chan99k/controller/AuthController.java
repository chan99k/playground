package chan99k.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import chan99k.controller.request.EncryptedUserRequestBody;
import chan99k.controller.request.SimpleOtpRequestBody;
import chan99k.service.OtpService;
import chan99k.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final OtpService otpService;
	private final UserService userService;

	@PostMapping("/api/v1/users/auth")
	public String auth(@RequestBody EncryptedUserRequestBody request) {
		return userService.auth(request.getUserId(), request.getPassword());
	}

	@PostMapping("/api/v1/otp/check")
	public boolean checkOtp(@RequestBody SimpleOtpRequestBody request) {
		return otpService.checkOtp(request.userId(), request.otp());
	}

}
