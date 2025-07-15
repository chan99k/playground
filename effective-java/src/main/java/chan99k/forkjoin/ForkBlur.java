package chan99k.forkjoin;

import java.util.concurrent.RecursiveAction;

/**
 * {@link RecursiveAction}을 상속받아 이미지에 흐림 효과를 적용합니다.
 * 이 클래스는 Fork/Join 프레임워크를 사용하여 작업을 병렬로 처리하도록 설계되었습니다.
 * 생성 시 지정된 방향(수평, 수직, 또는 동시)에 따라 블러 연산을 수행합니다.
 */
public class ForkBlur extends RecursiveAction {

	/**
	 * 흐림 효과를 적용할 픽셀 창의 너비 또는 높이입니다. 홀수여야 합니다.
	 */
	private static final int blurWidth = 15;
	/**
	 * 작업을 직접 처리할지, 아니면 더 작은 작업으로 분할할지를 결정하는 임계값입니다.
	 */
	protected static int threshold = 10000;

	private final int[] source;
	private final int[] destination;
	private final int start;
	private final int length;
	private final int width;
	private final BlurDirection direction;

	/**
	 * 블러를 적용할 방향을 정의하는 열거형입니다.
	 */
	public enum BlurDirection {
		HORIZONTAL,
		VERTICAL,
		HORIZONTAL_AND_VERTICAL
	}

	/**
	 * ForkBlur 인스턴스를 생성합니다.
	 *
	 * @param source      원본 이미지 픽셀 배열
	 * @param destination 결과 이미지 픽셀 배열
	 * @param start       처리 시작 인덱스
	 * @param length      처리할 길이
	 * @param width       이미지의 너비
	 * @param direction   블러 처리 방향
	 */
	public ForkBlur(int[] source, int[] destination, int start, int length, int width, BlurDirection direction) {
		this.source = source;
		this.destination = destination;
		this.start = start;
		this.length = length;
		this.width = width;
		this.direction = direction;
	}

	/**
	 * 작업을 직접 수행합니다. 설정된 방향에 따라 적절한 블러 메서드를 호출합니다.
	 */
	protected void computeDirectly() {
		switch (direction) {
			case HORIZONTAL:
				computeHorizontalBlur();
				break;
			case VERTICAL:
				computeVerticalBlur();
				break;
			case HORIZONTAL_AND_VERTICAL:
				compute2DBlur();
				break;
		}
	}

	/**
	 * 지정된 영역의 픽셀에 대해 수평 블러를 계산합니다.
	 */
	private void computeHorizontalBlur() {
		int sidePixels = (blurWidth - 1) / 2;
		for (int index = start; index < start + length; index++) {
			int y = index / width;
			int x = index % width;
			float r = 0, g = 0, b = 0;

			for (int i = -sidePixels; i <= sidePixels; i++) {
				int currentX = Math.min(Math.max(x + i, 0), width - 1);
				int mIndex = y * width + currentX;
				int pixel = source[mIndex];
				r += (float)((pixel & 0x00ff0000) >> 16);
				g += (float)((pixel & 0x0000ff00) >> 8);
				b += (float)(pixel & 0x000000ff);
			}

			r /= blurWidth;
			g /= blurWidth;
			b /= blurWidth;

			int dpixel = (0xff000000) | (((int)r) << 16) | (((int)g) << 8) | ((int)b);
			destination[index] = dpixel;
		}
	}

	/**
	 * 지정된 영역의 픽셀에 대해 수직 블러를 계산합니다.
	 */
	private void computeVerticalBlur() {
		int sidePixels = (blurWidth - 1) / 2;
		int height = source.length / width;
		for (int index = start; index < start + length; index++) {
			int y = index / width;
			int x = index % width;
			float r = 0, g = 0, b = 0;

			for (int j = -sidePixels; j <= sidePixels; j++) {
				int currentY = Math.min(Math.max(y + j, 0), height - 1);
				int mIndex = currentY * width + x;
				int pixel = source[mIndex];
				r += (float)((pixel & 0x00ff0000) >> 16);
				g += (float)((pixel & 0x0000ff00) >> 8);
				b += (float)(pixel & 0x000000ff);
			}

			r /= blurWidth;
			g /= blurWidth;
			b /= blurWidth;

			int dpixel = (0xff000000) | (((int)r) << 16) | (((int)g) << 8) | ((int)b);
			destination[index] = dpixel;
		}
	}

	/**
	 * 지정된 영역의 픽셀에 대해 수평 및 수직 블러를 동시에 계산합니다. (2D Box Blur)
	 */
	private void compute2DBlur() {
		int sidePixels = (blurWidth - 1) / 2;
		for (int index = start; index < start + length; index++) {
			// 현재 픽셀의 2D 좌표 계산
			int y = index / width;
			int x = index % width;

			float red = 0, green = 0, blue = 0;
			int pixelCount = 0;

			// 주변 픽셀들을 순회 (blurWidth x blurWidth 윈도우)
			for (int j = -sidePixels; j <= sidePixels; j++) {
				for (int i = -sidePixels; i <= sidePixels; i++) {
					int currentX = x + i;
					int currentY = y + j;

					// 이미지를 벗어나는 좌표는 무시
					if (currentX >= 0 && currentX < width && currentY >= 0 && currentY < source.length / width) {
						int mIndex = currentY * width + currentX;
						int pixel = source[mIndex];
						red += (float)((pixel & 0x00ff0000) >> 16);
						green += (float)((pixel & 0x0000ff00) >> 8);
						blue += (float)(pixel & 0x000000ff);
						pixelCount++;
					}
				}
			}

			// 평균값 계산
			red /= pixelCount;
			green /= pixelCount;
			blue /= pixelCount;

			// Re-assemble destination pixel.
			int dpixel = (0xff000000)
				| (((int)red) << 16)
				| (((int)green) << 8)
				| (((int)blue));
			destination[index] = dpixel;
		}
	}

	@Override
	protected void compute() {
		if (length < threshold) {
			computeDirectly();
			return;
		}

		int split = length / 2;

		invokeAll(
			new ForkBlur(source, destination, start, split, width, direction),
			new ForkBlur(source, destination, start + split, length - split, width, direction)
		);
	}
}
