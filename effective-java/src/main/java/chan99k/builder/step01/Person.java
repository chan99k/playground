package chan99k.builder.step01;

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

	public static void main(String[] args) {
		Person p1 = createPersonForTesting();
		System.out.println(p1.getFirstName());
	}

	public static Person createPersonForTesting() {
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAddressOne("address1");
		person.setAddressTwo("address12");
		person.setBirthDate(LocalDate.now());
		person.setSex("male");
		person.setDriverLicence(true);
		person.setMarried(true);
		// ... 멤버변수가 많다면 변수에 값 세팅이 어려워진다.
		return person;
	}

	public Person() {
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Person(String firstName, String lastName, LocalDate birthDate, String addressOne, String addressTwo,
		String sex,
		boolean driverLicence, boolean married) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.sex = sex;
		this.driverLicence = driverLicence;
		this.married = married;
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
}
