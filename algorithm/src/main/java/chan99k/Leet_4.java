package chan99k;

import java.util.ArrayList;
import java.util.List;

public class Leet_4 {
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int ptr1 = 0, ptr2 = 0;
		List<Integer> merged = new ArrayList<>();

		while (ptr1 < nums1.length && ptr2 < nums2.length) {
			if (nums1[ptr1] < nums2[ptr2]) {
				merged.add(nums1[ptr1]);
				ptr1++;
			} else {
				merged.add(nums2[ptr2]);
				ptr2++;
			}
		}

		// nums1에 남은 요소들을 추가
		while (ptr1 < nums1.length) {
			merged.add(nums1[ptr1]);
			ptr1++;
		}

		// nums2에 남은 요소들을 추가
		while (ptr2 < nums2.length) {
			merged.add(nums2[ptr2]);
			ptr2++;
		}

		int mid = merged.size() / 2;
		if (merged.size() % 2 == 0) {
			return (merged.get(mid) + merged.get(mid - 1)) / 2.0;
		}
		return merged.get(mid);
	}
}
