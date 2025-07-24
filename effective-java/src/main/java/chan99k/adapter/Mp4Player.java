package chan99k.adapter;

public class Mp4Player implements MediaPlayer {
	@Override
	public boolean supports(String audioType) {
		return "mp4".equalsIgnoreCase(audioType);
	}

	@Override
	public void play(String audioType, String fileName) {
		System.out.println("Playing mp4 file with dedicated player: " + fileName);
	}
}
