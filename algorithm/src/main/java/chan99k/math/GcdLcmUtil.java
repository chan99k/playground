package chan99k.math;

public class GcdLcmUtil {
	/**
	 * 유클리드 호제법을 이용한 최대공약수(Gcd) 계산 -
	 * "큰 수를 작은 수로 나눈 나머지를 이용해도 최대공약수는 같다" 라는 성질을 이용한다.
	 * <p>
	 * 1.	큰 수를 작은 수로 나눈다.
	 * <p>
	 * 2.	그 나머지를 다시 작은 수와 비교해 GCD를 구한다.
	 * <p>
	 * 3.	나머지가 0이 될 때, 그때의 나누는 수가 바로 GCD.
	 * @param a 큰 수
	 * @param b 작은 수
	 * @return 두 수의 최대공약수
	 */
	public static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	/**
	 * GCD를 이용한 최소공배수(LCM) 계산 - “두 수의 곱을 최대공약수로 나누면 최소공배수가 된다” 라는 성질을 이용한다.
	 * <p>
	 * a * b = gcd(a,b) * lcm(a,b) : 즉, 최대공약수와 최소공배수를 곱하면 두 수의 곲이 된다.
	 * @param a 첫 번째 수
	 * @param b 두 번째 수
	 * @return 두 수의 최소공배수
	 */
	public static long lcm(long a, long b) {
		if (a == 0 || b == 0) {
			return 0;
		}
		// 오버플로우 방지를 위해 (b / gcd(a, b))를 먼저 계산 : 실제로는 a*b / gcd(a,b) 와 같다.
		return Math.abs(a * (b / gcd(a, b)));
	}
}
