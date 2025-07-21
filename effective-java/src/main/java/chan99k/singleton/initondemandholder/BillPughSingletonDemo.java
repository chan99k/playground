package chan99k.singleton.initondemandholder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BillPughSingletonDemo {
	public static void main(String[] args) {
		try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
			for (int i = 0; i < 10; i++) {
				executorService.submit(() -> {
					BillPughSingleton instance = BillPughSingleton.getInstance();
					System.out.println("Instance : " + instance + " | Thread : " + Thread.currentThread().getName());
				});
			}

			executorService.shutdown();

			if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
			executorService.shutdownNow();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}