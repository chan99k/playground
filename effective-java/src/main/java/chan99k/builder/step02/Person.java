package chan99k.builder.step02;

import java.time.LocalDate;

/**
 * 여러가지 종류의 생성자를 용도에 맞게 매번 작성하는 것에는 무리가 있다.
 */
public class Person {
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private String addressOne;
	private String addressTwo;
	private String sex;
	private boolean driverLicence;
	private boolean married;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public boolean isDriverLicence() {
		return driverLicence;
	}

	public void setDriverLicence(boolean driverLicence) {
		this.driverLicence = driverLicence;
	}

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}

	public static PersonBuilder builder() {
		return new PersonBuilder();
	}
}
