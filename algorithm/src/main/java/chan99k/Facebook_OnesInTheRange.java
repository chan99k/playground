package chan99k;

public class Facebook_OnesInTheRange {
	int[] arr;
	int[] acc;
	long bits;

	public Facebook_OnesInTheRange(int[] arr) {
		this.arr = arr;
		this.acc = new int[arr.length];
		makeAccSumArr();
		
		if (arr.length <= 64) {
			initBitOptimized();
		}
	}

	/**
	 * 1과 0으로 이루어진 배열 arr이 있을 때,
	 * @param s start index
	 * @param e end index
	 * @return 주어진 인덱스 s와 e 사이에(inclusive) 존재하는 1의 개수
	 */
	public int numOfOnes(int s, int e) {
		if (s == 0) {
			return acc[e];
		}
		return acc[e] - acc[s - 1];
	}

	public int numOfOnesNaive(int s, int e) {
		int count = 0;
		for (int i = s; i <= e; i++) {
			count += arr[i];
		}
		return count;
	}

	public int numOfOnesBitOptimized(int s, int e) {
		if (arr.length > 64) {
			return numOfOnes(s, e);
		}
		
		long mask = createRangeMask(s, e);
		return Long.bitCount(bits & mask);
	}

	private void makeAccSumArr() {
		acc[0] = arr[0];
		for (int i = 1; i < acc.length; i++) {
			acc[i] = acc[i - 1] + arr[i];
		}
	}
	
	private void initBitOptimized() {
		bits = 0L;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 1) {
				bits |= (1L << i);
			}
		}
	}
	
	/**
	 * 구간 [start, end]에 해당하는 비트만 1로 설정된 마스크를 생성
	 * 예: start=2, end=5 → 0b111100 (2,3,4,5번 비트가 1)
	 */
	private long createRangeMask(int start, int end) {
		if (start > end || end >= 64) return 0L;
		
		int length = end - start + 1;
		long mask = (1L << length) - 1;  // length개의 연속된 1 생성 (예: length=4 → 0b1111)
		return mask << start;  // start 위치로 이동 (예: 0b1111 → 0b111100)
	}
}
