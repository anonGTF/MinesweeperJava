import javax.swing.*;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar {
    private JMenu difficulty, sound;
    private ButtonGroup difGroup, soundGroup;
    private JRadioButtonMenuItem easy, medium, hard, custom, soundOn, soundOff;

    public Menu(ActionListener component){
        easy = new JRadioButtonMenuItem("easy");
        medium = new JRadioButtonMenuItem("medium");
        hard = new JRadioButtonMenuItem("hard");
        custom = new JRadioButtonMenuItem("Custom...");
        soundOn = new JRadioButtonMenuItem("Sound on");
        soundOff = new JRadioButtonMenuItem("sound off");

        easy.setSelected(true);
        soundOn.setSelected(true);

        easy.addActionListener(component);
        medium.addActionListener(component);
        hard.addActionListener(component);
        custom.addActionListener(component);
        soundOn.addActionListener(component);
        soundOff.addActionListener(component);

        difficulty = new JMenu("Difficulty");
        sound = new JMenu("Sound");
        difGroup = new ButtonGroup();
        soundGroup = new ButtonGroup();

        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);
        difficulty.add(custom);
        sound.add(soundOn);
        sound.add(soundOff);

        difGroup.add(easy);
        difGroup.add(medium);
        difGroup.add(hard);
        difGroup.add(custom);
        soundGroup.add(soundOn);
        soundGroup.add(soundOff);

        this.add(difficulty);
        this.add(sound);
    }

    public JRadioButtonMenuItem getEasy() {
        return easy;
    }

    public JRadioButtonMenuItem getMedium() {
        return medium;
    }

    public JRadioButtonMenuItem getHard() {
        return hard;
    }

    public JRadioButtonMenuItem getCustom() {
        return custom;
    }

    public JRadioButtonMenuItem getSoundOn() {
        return soundOn;
    }

    public JRadioButtonMenuItem getSoundOff() {
        return soundOff;
    }
}
