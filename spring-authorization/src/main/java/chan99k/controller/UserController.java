package chan99k.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import chan99k.controller.request.EncryptedUserRequestBody;
import chan99k.domain.user.User;
import chan99k.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/api/v1/users")
	public User createNewUser(@RequestBody EncryptedUserRequestBody request) {
		return userService.createnewUser(request.getUserId(), request.getPassword());
	}

}
