package openhabConfiguration;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public class getConfigurations {


    public static String getConfigs(String sectionName, String keyName) {
        try {
            Ini ini = new Ini(new File("src/main/resources/windowsFiles/openHab.ini"));
            return ini.get(sectionName, keyName);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
