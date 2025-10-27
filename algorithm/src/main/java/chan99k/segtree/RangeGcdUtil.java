package chan99k.segtree;

public class RangeGcdUtil {
	static long[] arr; // 원본 데이터
	static long[] tree; // 세그먼트 트리

	/**
	 세그먼트 트리를 재귀적으로 초기화
	 각 노드는 자신이 담당하는 구간의 GCD 값을 저장
	 */
	static long init(int node, int start, int end) {
		if (start == end) {
			return tree[node] = arr[start];
		}

		int mid = start + (end - start) / 2;
		// 내부 노드: 두 자식 구간의 GCD를 구해 저장
		return tree[node] = gcd(init(node * 2, start, mid), init(node * 2 + 1, mid + 1, end));
	}

	static long query(int node, int start, int end, int left, int right) {
		if (left > end || right < start) {
			return 0; // gcd 의 항등원 - a와 0이 최소공배수를 구하면 그 결과는 항상 a 이다.
		}
		// 현재 노드구간이 찾는 검색 범위 구간의 내부에 완전히 들어가면
		if (left <= start && end <= right) {
			return tree[node];
		}

		// 범위가 일부만 겹칠 때
		int mid = start + (end - start) / 2;

		return gcd(query(node * 2, start, mid, left, right), query(node * 2 + 1, mid + 1, end, left, right));
	}

	static void update(int node, int start, int end, int index, int newValue) {
		if (index < start || end < index) { // 현재 노드의 구간이 아닐때
			return;
		}

		// [2] 리프 노드에 도달했을 때: 실제 값을 갱신하고 재귀 종료
		if (start == end) {
			arr[index] = newValue; // 원본 배열 갱신
			tree[node] = newValue; // 리프 노드 갱신
			return;
		}

		// [3] 내부 노드일 때: 하위 노드로 재귀 호출
		int mid = start + (end - start) / 2;

		// 갱신 대상이 포함된 자식 노드로 이동
		update(node * 2, start, mid, index, newValue); // 왼쪽 자식
		update(node * 2 + 1, mid + 1, end, index, newValue); // 오른쪽 자식

		// [4] 부모 노드의 GCD 값 갱신
		// 자식 노드의 GCD를 다시 GCD 연산하여 현재 노드를 갱신
		tree[node] = gcd(tree[node * 2], tree[node * 2 + 1]);
	}

	static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}
}
