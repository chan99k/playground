package chan99k.adapter;

public class AdapterDemo {
	public static void main(String[] args) {
		AudioPlayer audioPlayer = AudioPlayer.createDefault();

		audioPlayer.play("mp4", "back-in-black.mp4");
		audioPlayer.play("vlc", "master-of-puppets.vlc");
		// 클라이언트는 내부적으로 어댑터가 동작하는지 알지 못하고 그냥 play() 를 호출한다.
		audioPlayer.play("mp3", "song2.mp3");
		audioPlayer.play("avi", "STARS-051.avi");
	}
}
