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
    private static boolean co2 = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "co2"));
    private static boolean elec = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "electricity"));
    private static int qos = Integer.parseInt(getConfigurations.getConfigs("properties", "qos"));

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
        if(qos==0 || qos==1 || qos==2){
            bridgeString += "Bridge mqtt:broker:myWohnraumBroker \"myWohnraumBroker\" [host=\"192.168.178.22\", secure=false, qos="+ qos +"] \n{\n";
            bridgeString += buildThings(num);
            bridgeString += "}";
            return bridgeString;
        }
        else return null;
    }

    private String buildThings(int num) {
        for(int i = 1; i <= num; i++) {
            thingString += "    // Thing Sensoren Wohnraum" + i + "\n" +
                    "    Thing topic wohnraumSensoren" + i + " \"Wohnraum" + i + " Sensoren\" {\n" +
                    "    Channels:\n";
            if(temp) {
                thingString += "        Type number : temperature   \"Temperatur\"            [stateTopic=\"apartment/temperature" + i + "/\"]\n";
            }
            if(hum) {
                thingString += "        Type number : humidity      \"Luftfeuchtigkeit\"      [stateTopic=\"apartment/humidity" + i + "/\"]\n";
            }
            if(elec){
                thingString += "        Type number : electricity   \"Stromzähler\"           [stateTopic=\"apartment/electricity" + i + "/\"]\n";
            }
            if(co2){
                thingString += "        Type number : kohlenstoffdioxid   \"Kolhelnstoffdioxid\"           [stateTopic=\"apartment/kohlenstoffdioxid" + i + "/\"]\n";
            }
            thingString +=        "    }\n\n";
        }
        return thingString;
    }
}
