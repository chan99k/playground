package chan99k.experiments.domain;

public interface PasswordEncoder {
	boolean matches(String rawPassword, String currPassword);
}
