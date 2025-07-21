package chan99k.builder.step02;

import java.time.LocalDate;

public class BuilderDemo {
	public static void main(String[] args) {
		Person p1 = Person.builder()
			.firstName("FirstName")
			.lastName("lastName")
			.birthDate(LocalDate.now())
			.addressOne("add01")
			.addressTwo("add02")
			.driverLicence(true)
			.married(false)
			.sex("male")
			.build();

		System.out.println(p1.toString());

		Person p2 = Person.builder()
			.sex("female")
			.build();

		System.out.println(p2.toString());

	}

}
