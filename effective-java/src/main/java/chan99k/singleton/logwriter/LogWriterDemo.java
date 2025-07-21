package chan99k.singleton.logwriter;

public class LogWriterDemo {
	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			Thread t = new ThreadSub(i);
			t.start();
		}
	}

	static class ThreadSub extends Thread {
		int num;

		public ThreadSub(int num) {
			this.num = num;
		}

		@Override
		public void run() {
			LogWriter logger = LogWriter.getInstance();
			if (num < 10) {
				logger.log(LogWriter.LogLevel.INFO, "*** 0" + num + " ***" + " | " + Thread.currentThread().getName());
			} else {
				logger.log(LogWriter.LogLevel.INFO, "*** " + num + " ***" + " | " + Thread.currentThread().getName());
			}
		}
	}
}

