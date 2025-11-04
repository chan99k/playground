package chan99k.trees;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Boj_1135 {

	static List<Integer>[] children;

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] emp = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt).toArray();

		children = new List[N];
		for (int i = 0; i < N; i++) {
			children[i] = new ArrayList<>();
		}

		int root = 0;
		for (int i = 0; i < N; i++) {
			if (emp[i] == -1) {
				root = i;
			} else {
				children[emp[i]].add(i);
			}
		}

		// base 까지 내려간 다음, 자식들의 완료 시간 중 최대값을 구하되, 연락 순서를 고려해서 재귀적으로 반환
		int answer = recursive(root);

		System.out.println(answer);
	}

	private static int recursive(int node) {
		if (children[node].isEmpty()) // 자식이 없다면 0을 반환
			return 0;

		List<Integer> times = new ArrayList<>();
		for (int child : children[node]) {
			times.add(recursive(child));
		}

		times.sort(Comparator.reverseOrder()); // 자식별 결과를 내림차순

		int max = 0;
		for (int i = 0; i < times.size(); i++) {
			max = Math.max(max, times.get(i) + i + 1); // 자식에게 전화를 i초 후에 걸수 있음, 가장 큰 값이 해당 노드가 전파 완료되는데 걸린 시간
		}

		return max;
	}
}
