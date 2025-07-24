package chan99k.adapter;

/**
 * Adaptee 클래스 <br>
 * 호환되지 않는 기존 클래스.
 */
public class LegacyAudioPlayer {
	public void playMp3(String fileName) {
		System.out.println("Playing mp3 file (legacy): " + fileName);
	}
}
