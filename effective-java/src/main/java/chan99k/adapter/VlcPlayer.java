package chan99k.adapter;

public class VlcPlayer implements MediaPlayer {
	@Override
	public boolean supports(String audioType) {
		return "vlc".equalsIgnoreCase(audioType);
	}

	@Override
	public void play(String audioType, String fileName) {
		System.out.println("Playing vlc file with dedicated player: " + fileName);
	}
}
