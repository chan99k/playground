package chan99k.cycledetection;
/*
처음에는 bit 조작으로 시뮬레이션하듯 비트 뒤집고, 타겟 숫자만큼 1이 연속해서 나오는지 따라가려고 했는데, n <= 10^5 조건 보니까 그건 아니겠다 싶었습니다.
비트를 뒤집는게 중요한게 아니라 그룹핑하는 것이 중요하니까, 단계마다 1로 바뀔 때 인접그룹이 있는 지 확인하고 같은 그룹으로 묶고, 사이즈 업데이트 해주는 방식
*/
public class Leet_1562 {
	public int findLatestStep(int[] arr, int m) {
		int n = arr.length; // 1-based
		if (m == n)
			return n;

		BinaryString bs = new BinaryString(n);
		int[] parent = new int[n + 1];
		int[] size = new int[n + 1]; // 그룹의 크기 관리용

		for (int i = 1; i < n + 1; i++) {
			parent[i] = i;
		}

		int count = 0;
		int lastStep = -1;
		for (int i = 1; i < n + 1; i++) {
			int pos = arr[i - 1];
			bs.values[pos] = 1;
			size[pos] = 1;
			// 왼쪽
			if (pos > 1 && bs.values[pos - 1] == 1) {
				int leftSize = size[findParent(pos - 1, parent)];
				if (leftSize == m)
					count--;
				unionParent(pos - 1, pos, parent, size);
			}
			// 오른쪽
			if (pos < n && bs.values[pos + 1] == 1) {
				int rightSize = size[findParent(pos + 1, parent)];
				if (rightSize == m)
					count--;
				unionParent(pos + 1, pos, parent, size);
			}

			// 현재 단계의 변경으로 인해 변경된 그룹의 크기 확인
			if (size[findParent(pos, parent)] == m) {
				count++;
			}

			if (count > 0) {
				lastStep = i;
			}
		}

		return lastStep;
	}

	private int findParent(int x, int[] parent) {
		if (x != parent[x])
			return parent[x] = findParent(parent[x], parent);
		return parent[x];
	}

	private void unionParent(int a, int b, int[] parent, int[] size) {
		int pa = findParent(a, parent);
		int pb = findParent(b, parent);
		if (pa == pb)
			return;

		if (pa < pb) {
			parent[pb] = pa;
			size[pa] += size[pb];
		} else {
			parent[pa] = pb;
			size[pb] += size[pa];
		}

	}

	private static class BinaryString {
		final int[] values;

		BinaryString(int n) {
			this.values = new int[n + 1]; // 1-based
		}

		@Override
		public String toString() {
			var sb = new StringBuilder();
			for (int num : values) {
				sb.append(num);
			}
			return sb.toString();
		}
	}
}
