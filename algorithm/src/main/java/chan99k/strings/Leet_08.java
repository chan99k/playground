package chan99k.strings;

public class Leet_08 {
	public int myAtoi(String s) {
		String s1 = s.stripLeading();

		boolean isNegative = false;
		var sb = new StringBuilder();

		for (int i = 0; i < s1.length(); i++) {
			char c = s1.charAt(i);
			if (i == 0 && c == '-') {
				isNegative = true;
				continue;
			} else if (i == 0 && c == '+') {
				continue;
			}

			if (!Character.isDigit(c)) {
				break;
			}
			sb.append(c);
		}
		int answer;
		try {
			answer = Integer.parseInt(sb.toString());
			if (isNegative) {
				answer *= -1;
			}
		} catch (NumberFormatException e) {
			if (sb.toString().isEmpty()) {
				answer = 0;
			} else if (isNegative) {
				answer = Integer.MIN_VALUE;
			} else {
				answer = Integer.MAX_VALUE;
			}
		}
		return answer;
	}

	public static void main(String[] args) {
		var sol = new Leet_08();
		System.out.println(sol.myAtoi("+1")); // -2147483648
	}
}
