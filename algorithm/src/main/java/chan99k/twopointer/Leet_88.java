package chan99k.twopointer;

import java.util.Arrays;
import java.util.PrimitiveIterator;

public class Leet_88 {

	// 그냥 합치고 정렬 때려버려도 제약조건내에서 충분히 풀린다
	// m을 기준으로 양쪽은 오름차순 정렬이 되어 있기 때문에,
	public void mergeByIterator(int[] nums1, int m, int[] nums2, int n) {
		PrimitiveIterator.OfInt iter = Arrays.stream(nums2).iterator();
		for (int i = m; i < nums1.length; i++) {
			if (nums1[i] == 0 && iter.hasNext()) {
				nums1[i] = iter.nextInt();
			}
		}
		Arrays.sort(nums1);
	}

	public void mergeByPointers(int[] nums1, int m, int[] nums2, int n) {
		int i = m - 1; // n1 의 마지막 유효 원소 인덱스
		int j = n - 1; // n2 의 마지막 원소 인덱스
		int k = m + n - 1; // n1의 마지막 원소 인덱스

		// 뒤에서부터 병합 == 각 배열이 오름차순으로 정렬되어 있으므로 가능
		while (i >= 0 && j >= 0) {
			if (nums1[i] > nums2[j]) {
				nums1[k] = nums1[i];
				i--;
			} else {
				nums1[k] = nums2[j];
				j--;
			}
			k--;
		}

		// num2 에 아직 처리하지 않은 원소가 남아 있다면 == num1의 원소는 이제 다 오른쪽에 있음
		while (j >= 0) {
			nums1[k] = nums2[j];
			j--;
			k--;
		}
	}

	public static void main(String[] args) {
		Leet_88 leet88 = new Leet_88();
		// leet88.practice(new int[] {1,2,3,0,0,0}, 3, new int[] {2,5,6},3);
		leet88.mergeByIterator(new int[] {0}, 0, new int[] {2}, 1);
	}
}
