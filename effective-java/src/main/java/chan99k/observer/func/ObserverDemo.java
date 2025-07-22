package chan99k.observer.func;

import java.util.function.Consumer;

public class ObserverDemo {
	public static void main(String[] args) {
		Subject subject = new Subject();

		Consumer<String> logger = animal -> System.out.println("[LOG] 새로운 동물 추가됨: " + animal);
		Consumer<String> notifier = animal -> System.out.println(" 관리자에게 알림: " + animal + " 입고 완료");

		subject.registerListener(logger);
		subject.registerListener(notifier);

		subject.addAnimal("호랭이");

		System.out.println("\n--- 로거 리스너 해제 ---");
		subject.withdrawListener(logger);

		subject.addAnimal("고앵이");
	}
}
