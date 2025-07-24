package chan99k.adapter;

public interface MediaPlayer {
	/**
	 * 이 플레이어가 해당 오디오 타입을 지원하는지 여부를 반환합니다.
	 * @param audioType 오디오 포맷 (e.g., "mp3", "mp4")
	 * @return 지원하면 true, 그렇지 않으면 false
	 */
	boolean supports(String audioType);

	void play(String audioType, String fileName);

}
