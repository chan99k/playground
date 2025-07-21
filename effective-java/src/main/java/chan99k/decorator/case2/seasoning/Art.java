package chan99k.decorator.case2.seasoning;

import chan99k.decorator.case2.base.Student;

public class Art extends StudentDecorator {
	private Student student;

	public Art(Student student) {
		this.student = student;
	}

	@Override
	public String getDescription() {
		return student.getDescription() + " majored Art";
	}

	public void calculateStuff(){
		System.out.println("artistic calculation!");
	}
}
