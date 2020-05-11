import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class buildThings {

    private static String bridgeString = "";
    private static String thingString = "";
    private static int num = Integer.parseInt(getConfigurations.getConfigs("apartment", "number"));

    public boolean buildThingsFile(String path) {
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(path + "apartment.things")));
            pWriter.print(buildBridge());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (pWriter != null){
                pWriter.flush();
                pWriter.close();
            }
        }
    }

    private String buildBridge() {
        bridgeString += "Bridge mqtt:broker:myWohnraumBroker \"myWohnraumBroker\" [host=\"192.168.178.22\", secure=false] \n{\n";
        bridgeString += buildThings(num);
        bridgeString += "}";
        return bridgeString;
    }

    private String buildThings(int num) {
        for(int i = 1; i <= num; i++) {
            thingString += "    // Thing Sensoren Wohnraum" + i + "\n" +
                    "    Thing topic wohnraumSensoren" + i + " \"Wohnraum" + i + " Sensoren\" {\n" +
                    "    Channels:\n" +
                    "        Type number : temperature   \"Temperatur\"            [stateTopic=\"apartment" + i + "/temperature/\"]\n" +
                    "        Type number : humidity      \"Luftfeuchtigkeit\"      [stateTopic=\"apartment" + i + "/humidity/\"]\n" +
                    "    }\n" +
                    "\n" +
                    "    // Thing Actors Wohnraum" + i + "\n" +
                    "    Thing topic wohnraumActors" + i + " \"Wohnraum" + i + " Actors\" {\n" +
                    "    Channels:\n" +
                    "        Type dimmer : setHeater     \"Setzt Heizungs-Stufe\"  [commandTopic=\"apartment" + i + "/heater/setNewLevel/\", min=0, max=5]\n" +
                    "    }\n\n";
        }
        return thingString;
    }
}
