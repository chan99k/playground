package chan99k.singleton.logwriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bill Pugh Singleton 패턴을 사용하여 구현된 스레드 안전한 로거 클래스입니다.
 * 로그 레벨, 자동 리소스 관리(Shutdown Hook), 상세한 로그 포맷팅 기능을 제공합니다.
 * 애플리케이션 전체에서 단 하나의 인스턴스만 생성되어 사용됩니다.
 */
public class LogWriter {

	/**
	 * 로그의 심각도를 나타내는 레벨입니다.
	 */
	public enum LogLevel {
		INFO, WARN, ERROR
	}

	private final BufferedWriter bw;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * LogWriter의 private 생성자입니다.
	 * Singleton 패턴을 위해 외부에서 인스턴스 생성을 막습니다.
	 * 로그 파일을 append 모드로 열고, JVM 종료 시 자원을 자동으로 해제하는 Shutdown Hook을 등록합니다.
	 */
	private LogWriter() {
		BufferedWriter tempBw = null;
		try {
			// FileWriter를 append 모드(true)로 열어 기존 로그에 이어 씁니다.
			tempBw = new BufferedWriter(new FileWriter("log.txt", true));
		} catch (IOException e) {
			// 파일 초기화 실패 시 로깅이 불가능하므로, 에러를 표준 에러 스트림에 출력합니다.
			e.printStackTrace();
		}
		bw = tempBw;

		// JVM이 종료될 때 close() 메소드를 호출하여 자원을 안전하게 해제하도록 Shutdown Hook을 추가합니다.
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}

	/**
	 * Bill Pugh Singleton 패턴의 핵심 부분입니다.
	 * 이 내부 클래스는 {@link LogWriter#getInstance()}가 처음 호출될 때 로드되어
	 * 스레드 안전하게 INSTANCE를 초기화합니다.
	 */
	private static class SingletonHolder {
		private static final LogWriter INSTANCE = new LogWriter();
	}

	/**
	 * LogWriter의 유일한 인스턴스를 반환합니다.
	 *
	 * @return LogWriter의 싱글턴 인스턴스
	 */
	public static LogWriter getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 지정된 로그 레벨과 메시지를 파일에 기록합니다.
	 * 이 메소드는 synchronized 되어 있어 멀티스레드 환경에서 안전하게 사용할 수 있습니다.
	 * 로그 형식: "타임스탬프 [로그레벨] [스레드이름] 메시지"
	 *
	 * @param level   로그의 심각도 (INFO, WARN, ERROR)
	 * @param message 기록할 로그 메시지
	 */
	public void log(LogLevel level, String message) {
		// BufferedWriter가 초기화되지 않았다면 로깅을 시도하지 않습니다.
		if (bw == null) {
			System.err.println("Logger not initialized. Cannot log message: " + message);
			return;
		}

		synchronized (this) {
		try {
			String threadName = Thread.currentThread().getName();
			String formattedMessage = String.format(
				"%s [%-5s] [%-15s] %s %s",
				LocalDateTime.now().format(formatter),
				level,
				threadName,
				message,
				this
			);
			bw.write(formattedMessage);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// 로깅 실패 시, 관련 정보를 표준 에러 스트림에 출력합니다.
			// 파일에 예외를 다시 로깅하려 시도하면 무한 루프에 빠질 수 있으므로 이를 방지합니다.
			System.err.println("--- CRITICAL LOGGING FAILURE ---");
			System.err.println("Original message: [" + level + "] " + message);
			e.printStackTrace();
			System.err.println("---------------------------------");
		}
		}
	}

	/**
	 * BufferedWriter 리소스를 닫습니다.
	 * 이 메소드는 주로 JVM Shutdown Hook에 의해 자동으로 호출되지만, 수동으로 호출할 수도 있습니다.
	 */
	public synchronized void close() {
		try {
			if (bw != null) {
				log(LogLevel.INFO, "Closing log writer.");
				bw.close();
			}
		} catch (IOException e) {
			// 리소스 해제 실패 시 표준 에러 스트림에 출력합니다.
			e.printStackTrace();
		}
	}
}
