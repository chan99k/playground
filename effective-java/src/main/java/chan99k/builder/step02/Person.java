package chan99k.builder.step02;

import java.time.LocalDate;

/**
 * 여러가지 종류의 생성자를 용도에 맞게 매번 작성하는 것에는 무리가 있다.
 */
public class Person {
	private final String firstName;
	private final String lastName;
	private final LocalDate birthDate;
	private final String addressOne;
	private final String addressTwo;
	private final String sex;
	private final boolean driverLicence;
	private final boolean married;

	@Override
	public String toString() {
		return "Person{" +
			"firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", birthDate=" + birthDate +
			", addressOne='" + addressOne + '\'' +
			", addressTwo='" + addressTwo + '\'' +
			", sex='" + sex + '\'' +
			", driverLicence=" + driverLicence +
			", married=" + married +
			'}';
	}

	// Builder를 통해서만 생성할 수 있도록 private 생성자를 정의한다.
	private Person(PersonBuilder builder) {
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.birthDate = builder.birthDate;
		this.addressOne = builder.addressOne;
		this.addressTwo = builder.addressTwo;
		this.sex = builder.sex;
		this.driverLicence = builder.driverLicence;
		this.married = builder.married;
	}

	// 외부에서 빌더를 생성할 수 있는 static 팩토리 메서드
	public static PersonBuilder builder() {
		return new PersonBuilder();
	}

	// static nested 클래스로 Builder를 정의한다.
	public static class PersonBuilder {
		private String firstName;
		private String lastName;
		private LocalDate birthDate;
		private String addressOne;
		private String addressTwo;
		private String sex;
		private boolean driverLicence;
		private boolean married;

		// 필수값이 있다면 빌더의 생성자에서 받도록 설계할 수 있다.
		// public PersonBuilder(String firstName, String lastName) { ... }

		public PersonBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public PersonBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public PersonBuilder birthDate(LocalDate birthDate) {
			this.birthDate = birthDate;
			return this;
		}

		public PersonBuilder addressOne(String addressOne) {
			this.addressOne = addressOne;
			return this;
		}

		public PersonBuilder addressTwo(String addressTwo) {
			this.addressTwo = addressTwo;
			return this;
		}

		public PersonBuilder sex(String sex) {
			this.sex = sex;
			return this;
		}

		public PersonBuilder driverLicence(boolean driverLicence) {
			this.driverLicence = driverLicence;
			return this;
		}

		public PersonBuilder married(boolean married) {
			this.married = married;
			return this;
		}

		// private 생성자를 호출하여 최종 불변 객체를 생성한다.
		public Person build() {
			return new Person(this);
		}
	}
}
