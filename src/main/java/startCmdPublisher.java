import java.io.File;
import java.io.IOException;

public class startCmdPublisher {

    private static int NUMBER_TEMPERATUR = Integer.parseInt(getConfigurations.getConfigs("perApartment", "temperature"));
    private static int NUMBER_HUMIDITY = Integer.parseInt(getConfigurations.getConfigs("perApartment", "humidity"));
    private static int NUMBER_ELECTRICITY = Integer.parseInt(getConfigurations.getConfigs("perApartment", "electricity"));
    private static int NUMBER_HEATER = Integer.parseInt(getConfigurations.getConfigs("perApartment", "heater"));
    private static int NUMBER_APARTMENTS = Integer.parseInt(getConfigurations.getConfigs("apartment", "number"));

    private static File dir = new File("src/main/resources/PublisherAndSubscriber");

    public static boolean startPublisher(){

        try {

            if(NUMBER_TEMPERATUR > 0){
                for(int i = 1; i <= NUMBER_APARTMENTS; i++) {
                    ProcessBuilder pbTemp = new ProcessBuilder("cmd", "/c", "publishTemperature.bat " + i);
                    pbTemp.directory(dir);
                    pbTemp.start();
                }
            }

            if(NUMBER_HUMIDITY > 0){
                for(int i = 1; i <= NUMBER_APARTMENTS; i++) {
                    ProcessBuilder pbHum = new ProcessBuilder("cmd", "/c", "publishHumidity.bat " + i);
                    pbHum.directory(dir);
                    pbHum.start();
                }
            }

            if(NUMBER_ELECTRICITY > 0){
                for(int i = 1; i <= NUMBER_APARTMENTS; i++) {
                    ProcessBuilder pbElec = new ProcessBuilder("cmd", "/c", "publishElectricity.bat " + i);
                    pbElec.directory(dir);
                    pbElec.start();
                }
            }

            if(NUMBER_HEATER > 0){
                for(int i = 1; i <= NUMBER_APARTMENTS; i++) {
                    ProcessBuilder pbHeat = new ProcessBuilder("cmd", "/c", "subscribeHeater.bat " + i);
                    pbHeat.directory(dir);
                    pbHeat.start();
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
