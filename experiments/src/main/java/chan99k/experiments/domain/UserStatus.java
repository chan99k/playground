package chan99k.experiments.domain;

/**
 * 사용자의 생명주기 상태를 나타냅니다.
 */
public enum UserStatus {
	/**
	 * 사용자가 가입했지만 이메일 인증을 완료하지 않은 상태입니다.
	 */
	PENDING,

	/**
	 * 사용자가 완전히 등록되어 활성화된 상태입니다.
	 */
	ACTIVE,

	/**
	 * 사용자가 서비스를 탈퇴하여 더 이상 활성 상태가 아닌 경우입니다.
	 */
	DEACTIVATED
}