package chan99k.tobyspring.chap06.domain;

import lombok.Getter;

@Getter
public enum Level {
	GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);
	private final int value;
	private final Level next;

	Level(int value, Level next) {
		this.value = value;
		this.next = next;
	}

	public Level nextLevel() {
		return this.next;
	}

	public static Level valueOf(int value) {
		return switch (value) {
			case 1 -> BASIC;
			case 2 -> SILVER;
			case 3 -> GOLD;
			default -> throw new AssertionError("Unknown value: " + value);
		};
	}
}
