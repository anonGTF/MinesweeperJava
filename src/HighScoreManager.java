import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class HighScoreManager {
    private final static String FILENAME = "src/assets/highscore.txt";
    private HashMap<String, Integer> highScore = new HashMap<String, Integer>();

    public HighScoreManager(){
        try {
            File myObj = new File(FILENAME);
            Scanner scanner = new Scanner(myObj);
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(":");
                highScore.put(data[0], Integer.parseInt(data[1]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> getHighScore() {
        return highScore;
    }

    public void updateHighScore(){
        try {
            File file = new File(FILENAME);
            if (file.delete()) {
                System.out.println("Deleted the file: " + file.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
            FileWriter fileWriter = new FileWriter(FILENAME);
            highScore.forEach((key, value) -> {
                try {
                    fileWriter.write(key +":"+ value+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
