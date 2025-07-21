package chan99k.decorator.case2.seasoning;

import chan99k.decorator.case2.base.Student;

public class Science extends StudentDecorator {
	Student student;

	public Science(Student student) {
		this.student = student;
	}

	@Override
	public String getDescription() {
		return student.getDescription() + " majored Science";
	}

	public void calculateStuff() {
		System.out.println("scientific calculation!");
	}
}
