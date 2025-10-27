package chan99k.segtree;

public class RangeLcmUtil {
	static long[] arr;
	static long[] tree;

	static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	static long lcm(long a, long b) {
		if (a == 0 || b == 0) {
			return 0;
		}
		return a * (b / gcd(a, b));
	}

	static long init(int node, int start, int end) {
		if (start == end) {
			return tree[node] = arr[start];
		}

		int mid = start + (end - start) / 2;
		return tree[node] = lcm(init(node * 2, start, mid), init(node * 2 + 1, mid + 1, end));
	}

	static long query(int node, int start, int end, int left, int right) {
		// 중요: LCM의 항등원은 1입니다. lcm(a, 1) = a이기 때문입니다
		if (left > end || right < start) {
			return 1;
		}

		// 현재 노드의 구간이 검색 구간 내부에 완전히 들어가면
		if (left <= start && end <= right) {
			return tree[node];
		}

		int mid = start + (end - start) / 2;

		return lcm(query(node * 2, start, mid, left, right), query(node * 2 + 1, mid + 1, end, left, right));
	}

	static void update(int node, int start, int end, int index, int newValue) {
		if (index < start || end < index) {
			return;
		}

		if (start == end) { // 리프노드인 경우
			arr[index] = newValue;
			tree[node] = newValue;
			return;
		}
		// 자식 갱신해주고
		int mid = start + (end - start) / 2;
		update(node * 2, start, mid, index, newValue);
		update(node * 2 + 1, mid + 1, end, index, newValue);

		tree[node] = lcm(tree[node * 2], tree[node * 2 + 1]);
	}
}
