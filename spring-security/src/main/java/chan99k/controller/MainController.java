package chan99k.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@GetMapping("/main")
	public String getMain() {
		return "main.html";
	}

	@PostMapping("/add")
	public String add(@RequestParam String name) {
		log.info("POST /add. name={}", name);
		return "main.html";
	}
}
