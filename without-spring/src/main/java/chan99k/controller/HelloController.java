package chan99k.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello API 컨트롤러
 * REST API 엔드포인트 제공
 */
@RestController
@RequestMapping("/api")
public class HelloController {

	/**
	 * Hello 엔드포인트
	 * GET /api/hello 요청 시 "hello world" 문자열 반환
	 * @return "hello world" 문자열
	 */
	@GetMapping(value = "/hello", produces = "text/plain")
	public String hello() {
		return "hello world";
	}
}
