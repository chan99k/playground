package chan99k.forkjoin;

import static chan99k.forkjoin.ForkBlur.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

import javax.imageio.ImageIO;

/**
 * Fork/Join 프레임워크를 사용하여 이미지에 흐림 효과를 적용하는 메인 클래스입니다.
 * 이미지를 읽고, {@link ForkBlur} 작업을 생성하여 {@link ForkJoinPool}에서 실행한 후,
 * 결과 이미지를 파일로 저장하는 전체 과정을 관리합니다.
 */
public class ForkJoinMain {
	/**
	 * 애플리케이션의 메인 진입점입니다.
	 * 이미지 파일을 로드하고, 흐림 효과를 적용한 후, 결과 이미지를 저장합니다.
	 *
	 * @param args 커맨드 라인 인자 (사용되지 않음)
	 * @throws IOException 이미지 파일 처리 중 오류가 발생할 경우
	 */
	public static void main(String[] args) throws IOException {
		String srcName = "/Users/chan99/IdeaProjects/playground/effective-java/src/main/resources/chan99k/forkjoin/red-tulips.jpg";

		File srcFile = new File(srcName);

		// 파일이 존재하는지 확인
		if (!srcFile.exists()) {
			throw new IOException("이미지 파일을 찾을 수 없습니다: " + srcName);
		}

		BufferedImage image = ImageIO.read(srcFile);

		System.out.println("Source image: " + srcName);

		BufferedImage blurredImage = blur(image);

		String dstName = "blurred-tulips.jpg";
		File dstFile = new File(dstName);
		ImageIO.write(blurredImage, "jpg", dstFile);

		System.out.println("Output image: " + dstName);
	}

	/**
	 * 주어진 {@link BufferedImage}에 흐림 효과를 적용합니다.
	 * 이미지의 픽셀 데이터를 추출하여 {@link ForkBlur} 작업을 생성하고,
	 * 시스템의 가용 프로세서 수에 맞춰 생성된 {@link ForkJoinPool}을 사용하여 병렬 처리합니다.
	 * 처리 시간도 함께 측정하여 출력합니다.
	 *
	 * @param srcImage 원본 이미지
	 * @return 흐림 효과가 적용된 새로운 이미지
	 */
	public static BufferedImage blur(BufferedImage srcImage) {
		int w = srcImage.getWidth();
		int h = srcImage.getHeight();

		int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
		int[] dst = new int[src.length];

		System.out.println("Array size is " + src.length);
		System.out.println("Threshold is " + threshold);

		int processors = Runtime.getRuntime().availableProcessors();
		System.out.println(processors + " processor"
			+ (processors != 1 ? "s are " : " is ")
			+ "available");

		// --- Option 1: 수평/수직 동시 2D 블러 (단순하지만 비효율적) ---
		System.out.println("Using 2D blur method.");
		ForkBlur blurTask = new ForkBlur(src, dst, 0, src.length, w, ForkBlur.BlurDirection.HORIZONTAL_AND_VERTICAL);
		long startTime = System.currentTimeMillis();
		try (ForkJoinPool pool = new ForkJoinPool()) {
			pool.invoke(blurTask);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total image blur took " + (endTime - startTime) + " milliseconds.");

		BufferedImage dstImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		dstImage.setRGB(0, 0, w, h, dst, 0, w);

		return dstImage;
	}
}
