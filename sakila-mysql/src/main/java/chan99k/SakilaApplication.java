package chan99k;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Sakila Sample Database Reference
 * <a href="https://dev.mysql.com/doc/sakila/en/">...</a>
 */
@EnableJpaRepositories(basePackages = "chan99k.adapter.persistence.repository")
@SpringBootApplication
public class SakilaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SakilaApplication.class, args);
	}

}
