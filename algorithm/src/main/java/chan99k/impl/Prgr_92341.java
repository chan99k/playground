package chan99k.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Prgr_92341 {
	public int[] solution(int[] fees, String[] records) {
		int defaultTime = fees[0];
		int defaultFee = fees[1];
		int unitTime = fees[2];
		int unitFee = fees[3];

		Map<String, Integer> parkingLot = new HashMap<>();
		Map<String, Integer> totalTimes = new TreeMap<>();

		for (String record : records) {
			String[] parts = record.split(" ");
			int time = timeToMinutes(parts[0]);
			String carNumber = parts[1];
			String action = parts[2];

			if (action.equals("IN")) {
				parkingLot.put(carNumber, time);
			} else {
				int inTime = parkingLot.remove(carNumber);
				int duration = time - inTime;
				totalTimes.put(carNumber, totalTimes.getOrDefault(carNumber, 0) + duration);
			}
		}

		int endOfDay = timeToMinutes("23:59");
		for (Map.Entry<String, Integer> entry : parkingLot.entrySet()) {
			String carNumber = entry.getKey();
			int inTime = entry.getValue();
			int duration = endOfDay - inTime;
			totalTimes.put(carNumber, totalTimes.getOrDefault(carNumber, 0) + duration);
		}

		int[] result = new int[totalTimes.size()];
		int i = 0;
		for (int time : totalTimes.values()) {
			result[i++] = calculateFee(time, defaultTime, defaultFee, unitTime, unitFee);
		}

		return result;
	}

	private int timeToMinutes(String time) {
		String[] parts = time.split(":");
		return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
	}

	private int calculateFee(int totalTime, int defaultTime, int defaultFee, int unitTime, int unitFee) {
		if (totalTime <= defaultTime) {
			return defaultFee;
		}
		return defaultFee + (int)Math.ceil((double)(totalTime - defaultTime) / unitTime) * unitFee;
	}
}
