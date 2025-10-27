package chan99k.segtree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 특정 구간에 대한 최댓값을 저장하는 세그먼트 트리를 만들면 되겠구나!!
public class Boj_1725 {
	static int[] arr;
	static int[] tree;

	// 세그먼트 트리 초기화: 구간 내 최소 높이의 인덱스를 저장
	static int init(int node, int start, int end) {
		if (start == end) {
			return tree[node] = start;
		}

		int mid = start + (end - start) / 2;
		int left = init(node * 2, start, mid);
		int right = init(node * 2 + 1, mid + 1, end);

		// 높이가 더 작은 인덱스를 저장
		return tree[node] = (arr[left] <= arr[right]) ? left : right;
	}

	// 구간 [left, right]에서 최소 높이의 인덱스 반환
	static int query(int node, int start, int end, int left, int right) {
		if (left > end || right < start) {
			return -1;
		}

		if (left <= start && end <= right) {
			return tree[node];
		}

		int mid = start + (end - start) / 2;
		int leftIdx = query(node * 2, start, mid, left, right);
		int rightIdx = query(node * 2 + 1, mid + 1, end, left, right);

		if (leftIdx == -1)
			return rightIdx;
		if (rightIdx == -1)
			return leftIdx;

		return (arr[leftIdx] <= arr[rightIdx]) ? leftIdx : rightIdx;
	}

	static long getMaxArea(int left, int right) {
		if (left > right) {
			return 0;
		}

		// 구간 내 최소 높이의 인덱스 찾기
		int minIdx = query(1, 0, arr.length - 1, left, right);

		// 전체 구간을 사용하는 경우
		long area = (long)arr[minIdx] * (right - left + 1);

		// 왼쪽 구간의 최댓값
		long leftArea = getMaxArea(left, minIdx - 1);

		// 오른쪽 구간의 최댓값
		long rightArea = getMaxArea(minIdx + 1, right);

		return Math.max(area, Math.max(leftArea, rightArea));
	}

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		arr = new int[N + 1];
		tree = new int[N * 4];
		for (int i = 1; i < N + 1; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		init(1, 0, N - 1);

		long result = getMaxArea(0, N - 1);
		System.out.println(result);
	}
}
