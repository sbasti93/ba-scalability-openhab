import java.io.File;
import java.io.IOException;

public class startCmdPublisher {

    public static boolean startPublisher(int number){

        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "startPublisher.bat " +  number);
        File dir = new File("C:/Users/Basti/IdeaProjects/BAMavenTest/src/main/resources/batchFiles");
        pb.directory(dir);
        try {
            Process p = pb.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
