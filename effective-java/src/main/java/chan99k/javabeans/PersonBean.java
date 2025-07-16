package chan99k.javabeans;

import java.io.Serializable;

/**
 * JavaBean 명세를 따르는 예제 클래스.
 * 1. public 기본 생성자를 가진다.
 * 2. 필드들은 private 으로 선언된다.
 * 3. 필드들에 대한 public getter/setter 메서드를 가진다.
 * 4. (선택) Serializable 인터페이스를 구현한다.
 */
public class PersonBean implements Serializable {

    private String name;
    private int age;
    private boolean isStudent;

    // 1. public 기본 생성자
    public PersonBean() {
    }

    // 3. Getter/Setter 메서드
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // boolean 타입의 getter는 'is'로 시작할 수 있다.
    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }
}
