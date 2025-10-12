package chan99k.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leet_1604 {
	public List<String> alertNames(String[] keyName, String[] keyTime) {
		Map<String, List<int[]>> hm = new HashMap<>();

		for (int i = 0; i < keyName.length; i++) {
			hm.computeIfAbsent(keyName[i], k -> new ArrayList<>())
				.add(parseTime(keyTime[i]));
		}

		List<String> result = new ArrayList<>();

		for (Map.Entry<String, List<int[]>> entry : hm.entrySet()) {
			entry.getValue().sort((o1, o2) -> {
				if (o1[0] == o2[0])
					return o1[1] - o2[1];
				return o1[0] - o2[0];
			});

			List<int[]> value = entry.getValue();
			for (int i = 2; i < value.size(); i++) {
				int[] first = value.get(i - 2);
				int[] third = value.get(i);

				int minutes1 = first[0] * 60 + first[1];
				int minutes3 = third[0] * 60 + third[1];

				if (minutes3 - minutes1 <= 60) {
					result.add(entry.getKey());
					break;
				}
			}
		}

		return result.stream().sorted().toList();
	}

	private int[] parseTime(String time) {
		return Arrays.stream(time.split(":")).mapToInt(Integer::parseInt).toArray();
	}

	public static void main(String[] args) {
		var sol = new Leet_1604();
		System.out.println(sol.alertNames(new String[] {"daniel", "daniel", "daniel", "luis", "luis", "luis", "luis"},
			new String[] {"10:00", "10:40", "11:00", "09:00", "11:00", "13:00", "15:00"}));
	}
}
