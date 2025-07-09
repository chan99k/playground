package chan99k.experiments.domain;

/**
 * 엔티티가 작성자(author)를 가지고 소유권을 확인할 수 있는 '역할'을 정의하는 인터페이스입니다.
 * 클래스의 상속 관계와 무관하게 '소유 가능'이라는 행위를 부여할 수 있습니다.
 */
public interface Authorable {

	/**
	 * 이 엔티티의 작성자(User)를 반환해야 합니다.
	 * @return 작성자 User 객체. 작성자가 없는 경우(예: 비회원 댓글) null일 수 있습니다.
	 */
	User getAuthor();

	/**
	 * 이 엔티티의 소유자가 주어진 사용자인지 확인합니다.
	 * 이 default 메서드를 통해 중복 코드를 완벽하게 제거합니다.
	 * @param user 확인할 사용자
	 * @return 소유자가 맞으면 true
	 */
	default boolean isOwnedBy(User user) {
		// getAuthor()가 null을 반환할 수 있는 경우(예: Comment)와
		// 비교 대상 user가 null인 경우를 모두 안전하게 처리합니다.
		if (user == null || getAuthor() == null) {
			return false;
		}
		return getAuthor().getId().equals(user.getId());
	}
}