package chan99k.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
	@PostMapping("/test")
	public String test() {
		log.info("test method called");
		return "hello";
	}
}
