package chan99k.map;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Leet_391 {
	public boolean isRectangleCover(int[][] rectangles) {
		Set<Point> points = new HashSet<>();
		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		int totalArea = 0;

		for (int[] rect : rectangles) {
			Point p1 = new Point(rect[0], rect[1]);
			Point p2 = new Point(rect[0], rect[3]);
			Point p3 = new Point(rect[2], rect[1]);
			Point p4 = new Point(rect[2], rect[3]);

			for (Point p : List.of(p1, p2, p3, p4)) {
				if (!points.add(p))
					points.remove(p);
			}

			totalArea += squareArea(rect[0], rect[1], rect[2], rect[3]);

			minX = Math.min(minX, rect[0]);
			minY = Math.min(minY, rect[1]);
			maxX = Math.max(maxX, rect[2]);
			maxY = Math.max(maxY, rect[3]);
		}

		// 꼭짓점이 딱 4개만 남아야 겹치는 지점 없이 완벽한 사각형
		if (points.size() != 4)
			return false;

		if (checkBorder(points, minX, minY, maxX, maxY)) {
			return false;
		}

		return totalArea == squareArea(minX, minY, maxX, maxY);
	}

	private boolean checkBorder(Set<Point> points, int minX, int minY, int maxX, int maxY) {
		return !points.contains(new Point(minX, minY))
			|| !points.contains(new Point(minX, maxY))
			|| !points.contains(new Point(maxX, minY))
			|| !points.contains(new Point(maxX, maxY));
	}

	private int squareArea(int x1, int y1, int x2, int y2) {
		return getAnInt(x1, y1, x2, y2);
	}

	private static int getAnInt(int x1, int y1, int x2, int y2) {
		return (x2 - x1) * (y2 - y1);
	}

	private record Point(int x, int y) {
	}
}
