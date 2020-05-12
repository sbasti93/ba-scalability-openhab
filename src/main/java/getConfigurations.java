import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class getConfigurations {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static String getConfigs(String sectionName, String keyName) {
        try {
            Ini ini = new Ini(new File("src/main/resources/windowsFiles/openHab.ini"));
            return ini.get(sectionName, keyName);
        } catch (IOException e) {
            logger.severe("Failed to get ini's!");
            e.printStackTrace();
            return "";
        }
    }
}
