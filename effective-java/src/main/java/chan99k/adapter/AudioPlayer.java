package chan99k.adapter;

import java.util.List;

public class AudioPlayer {
	private final List<MediaPlayer> players;

	private AudioPlayer(List<MediaPlayer> players) {
		this.players = players;
	}

	public void play(String audioType, String fileName) {
		for (MediaPlayer player : players) {
			if (player.supports(audioType)) {
				player.play(audioType, fileName); // 지원하는 플레이어를 찾으면 즉시 작업을 위임하고 메서드를 종료
				return;
			}
		}
		// 모든 플레이어를 순회했지만 지원하는 플레이어를 찾지 못한 경우
		System.out.println("Invalid media. " + audioType + " format not supported");
	}

	/**
	 * 필요한 모든 플레이어(어댑터)를 등록하여 완전히 구성된 AudioPlayer를 생성하는 팩토리 메서드
	 */
	public static AudioPlayer createDefault() {
		List<MediaPlayer> allPlayers = List.of(
			new Mp4Player(),
			new VlcPlayer(),
			new Mp3Adapter(new LegacyAudioPlayer())
		);
		return new AudioPlayer(allPlayers);
	}
}
