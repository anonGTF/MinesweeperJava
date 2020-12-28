import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {

    private Clip leftClickedSfx, rightClickedSfx, gameOverSfx, gameWonSfx;

    public AudioManager() {
        try{
            leftClickedSfx = AudioSystem.getClip();
            leftClickedSfx.open(getAudioStream("src/assets/left_click.wav"));

            rightClickedSfx = AudioSystem.getClip();
            rightClickedSfx.open(getAudioStream("src/assets/right_click.wav"));

            gameOverSfx = AudioSystem.getClip();
            gameOverSfx.open(getAudioStream("src/assets/game_over.wav"));

            gameWonSfx = AudioSystem.getClip();
            gameWonSfx.open(getAudioStream("src/assets/game_won.wav"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void leftClickedSfxPlay(){
        leftClickedSfx.setMicrosecondPosition(0);
        leftClickedSfx.start();
    }

    public void rightClickedSfxPlay(){
        rightClickedSfx.setMicrosecondPosition(0);
        rightClickedSfx.start();
    }

    public void gameOverSfxPlay(){
        gameOverSfx.setMicrosecondPosition(0);
        gameOverSfx.start();
    }

    public void gameWonSfxPlay(){
        gameWonSfx.setMicrosecondPosition(0);
        gameWonSfx.start();
    }

    private AudioInputStream getAudioStream(String pathname) throws IOException, UnsupportedAudioFileException {
        File file = new File(pathname);
        return AudioSystem.getAudioInputStream(file);
    }
}
