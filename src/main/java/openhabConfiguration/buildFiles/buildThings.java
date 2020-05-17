package openhabConfiguration.buildFiles;

import openhabConfiguration.getConfigurations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class buildThings {

    private static String bridgeString = "";
    private static String thingString = "";
    private static int num = Integer.parseInt(getConfigurations.getConfigs("apartment", "number"));
    private static boolean temp = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "temperature"));
    private static boolean hum = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "humidity"));
    private static boolean heat = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "heater"));
    private static boolean elec = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "electricity"));

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
                    "    Channels:\n";
            if(temp) {
                thingString += "        Type number : temperature   \"Temperatur\"            [stateTopic=\"apartment/temperature/\"]\n";
            }
            if(hum) {
                thingString += "        Type number : humidity      \"Luftfeuchtigkeit\"      [stateTopic=\"apartment/humidity/\"]\n";
            }
            if(elec){
                thingString += "        Type number : electricity   \"Stromzähler\"           [stateTopic=\"apartment/electricity/\"]\n";
            }
            thingString +=        "    }\n\n";
            if(heat) {
                thingString += "    // Thing Actors Wohnraum" + i + "\n" +
                        "    Thing topic wohnraumActors" + i + " \"Wohnraum" + i + " Actors\" {\n" +
                        "    Channels:\n" +
                        "        Type dimmer : setHeater     \"Setzt Heizungs-Stufe\"  [commandTopic=\"apartment/heater/setNewLevel/\", min=0, max=5]\n" +
                        "    }\n\n";
            }
        }
        return thingString;
    }
}
