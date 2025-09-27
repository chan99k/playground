package chan99k.binsearch;

import java.util.Arrays;

public class Leet_704 {
	public int search(final int[] nums,final int target) {
		return Arrays.binarySearch(nums, target) >= 0  // TODO : 삼항연산자에 같은 메서드를 중복 호출하는데, 이를 자바 컴파일러가 최적화를 해줄까?
			? Arrays.binarySearch(nums, target)
			: -1;
	}
}
