package chan99k.adapter;

/**
 * Adapter 클래스
 */
public class Mp3Adapter implements MediaPlayer {
	private final LegacyAudioPlayer legacyAudioPlayer;

	public Mp3Adapter(LegacyAudioPlayer legacyAudioPlayer) {
		this.legacyAudioPlayer = legacyAudioPlayer;
	}

	@Override
	public boolean supports(String audioType) {
		return "mp3".equalsIgnoreCase(audioType);
	}

	@Override
	public void play(String audioType, String fileName) {
		legacyAudioPlayer.playMp3(fileName);
	}
}
