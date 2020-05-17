package openhabConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.logging.Logger;

public class startCmdPublisher {

    private static boolean NUMBER_TEMPERATUR = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "temperature"));
    private static boolean NUMBER_HUMIDITY = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "humidity"));
    private static boolean NUMBER_ELECTRICITY = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "electricity"));
    private static boolean NUMBER_HEATER = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "heater"));
    private static int NUMBER_APARTMENTS = Integer.parseInt(getConfigurations.getConfigs("apartment", "number"));

    private static File dir = new File("D:\\BA-Repo\\ba-scalability-openhab\\src\\main\\resources\\PublisherAndSubscriber\\");
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static boolean startPublisher(){

        try {

            if(NUMBER_TEMPERATUR){
                for(int i = 1; i <= NUMBER_APARTMENTS; i++) {

                    Process process;
                    process = Runtime.getRuntime().exec("cmd /c D:\\BA-Repo\\ba-scalability-openhab\\src\\main\\resources\\PublisherAndSubscriber\\publishTemperature.bat ");
                    process.destroy();
                    process.waitFor();

                    /*ProcessBuilder pbTemp = new ProcessBuilder("cmd", "/c", "publishTemperature.bat " + i);
                    pbTemp.directory(dir);
                    pbTemp.start();*/
                }
            }

            if(NUMBER_HUMIDITY){
                for(int i = 1; i <= NUMBER_APARTMENTS; i++) {
                    ProcessBuilder pbHum = new ProcessBuilder("cmd", "/c", "publishHumidity.bat " + i);
                    pbHum.directory(dir);
                    pbHum.start();
                }
            }

            if(NUMBER_ELECTRICITY){
                for(int i = 1; i <= NUMBER_APARTMENTS; i++) {
                    ProcessBuilder pbElec = new ProcessBuilder("cmd", "/c", "publishElectricity.bat " + i);
                    pbElec.directory(dir);
                    pbElec.start();
                }
            }

            if(NUMBER_HEATER){
                for(int i = 1; i <= NUMBER_APARTMENTS; i++) {
                    ProcessBuilder pbHeat = new ProcessBuilder("cmd", "/c", "subscribeHeater.bat " + i);
                    pbHeat.directory(dir);
                    pbHeat.start();
                }
            }

            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
