package chan99k.tobyspring.appendix_b.factorybean;

public class Message {
	String text;

	/**
	 * 생성자가 private이고 정적 팩토리 메서드로만 객체를 생성할 수 있으므로 이 클래스를 직접 스프링 빈으로 등록할 수 없다.
	 * @param text
	 */
	private Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static Message getMessage(String text) {
		return new Message(text);
	}
}
