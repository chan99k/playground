package chan99k.backtracking;

import java.util.ArrayList;
import java.util.List;

public class Leet_22 {

	List<String> result = new ArrayList<String>();

	public List<String> generateParenthesis(int n) {

		if (n <= 0)
			return result;
		backtrack(new StringBuilder(), 0, 0, n);
		return result;
	}

	private void backtrack(StringBuilder sb, int open, int close, int n) {
		if (sb.length() == 2 * n) {
			result.add(sb.toString());
			return;
		}
		if (open < n) {
			sb.append('(');
			backtrack(sb, open + 1, close, n);
			sb.deleteCharAt(sb.length() - 1);
		}

		if (close < open) {
			sb.append(')');
			backtrack(sb, open, close + 1, n);
			sb.deleteCharAt(sb.length() - 1);
		}
	}
}