import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerformanceResult {

	private final String method;
	private final List<Long> executionTimes;

	public PerformanceResult(String method, List<Long> executionTimes) {
		this.method = method;
		this.executionTimes = new ArrayList<>(executionTimes);
	}

	public String summary() {
		return String.format(
			"[%s] 평균: %.2f ms, 중앙값: %.2f ms, 표준편차: %.2f, 최소: %d, 최대: %d, 95th: %d",
			method, getAverage(), getMedian(), getStandardDeviation(),
			getMin(), getMax(), get95thPercentile()
		);
	}

	public double getAverage() {
		return executionTimes.stream()
			.mapToLong(Long::longValue)
			.average()
			.orElse(0.0);
	}

	public double getMedian() {
		List<Long> sorted = new ArrayList<>(executionTimes);
		Collections.sort(sorted);
		int size = sorted.size();

		if (size % 2 == 0) {
			return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
		} else {
			return sorted.get(size / 2);
		}
	}

	public double getStandardDeviation() {
		double mean = getAverage();
		double variance = executionTimes.stream()
			.mapToDouble(time -> Math.pow(time - mean, 2))
			.average()
			.orElse(0.0);
		return Math.sqrt(variance);
	}

	public long getMin() {
		return executionTimes.stream()
			.mapToLong(Long::longValue)
			.min()
			.orElse(0L);
	}

	public long getMax() {
		return executionTimes.stream()
			.mapToLong(Long::longValue)
			.max()
			.orElse(0L);
	}

	public long get95thPercentile() {
		List<Long> sorted = new ArrayList<>(executionTimes);
		Collections.sort(sorted);
		int index = (int)Math.ceil(0.95 * sorted.size()) - 1;
		return sorted.get(index);
	}
}