package openhabConfiguration.buildFiles;

import openhabConfiguration.getConfigurations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class buildItems {

    private static String groupHouse = "// Group for whole House\nGroup House\n\n";
    private static String groupApartments = "";
    private static String buildApartments = "";
    private static int NUM = Integer.parseInt(getConfigurations.getConfigs("apartment", "number"));
    private static boolean TEMP = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "temperature"));
    private static boolean HUM = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "humidity"));
    private static boolean HEATER = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "heater"));
    private static boolean ELEC = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "electricity"));

    public boolean buildItemsFile(String path) {
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(path + "apartment.items")));
            pWriter.print(groupHouse);
            pWriter.print(getGroupApartments());
            pWriter.print(getBuildApartments());
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

    private String getGroupApartments(){
        groupApartments += "// Group per apartment\n";
        for(int i=1; i <= NUM; i++){
            groupApartments += "Group Wohnraum" + i + " (House)\n";
        }
        groupApartments += "\n";
        return groupApartments;
    }

    private String getBuildApartments(){
        for(int i=1; i <= NUM; i++) {
            buildApartments += "\n// Wohnraum" + i + "\n";
            if(TEMP) {
                buildApartments += "Number Wohnraum" + i + "_Temperature    \"Temperatur [%.1f °C]\"          <temperature>   " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumSensoren" + i + ":temperature\"}\n";
            }
            if(HUM) {
                buildApartments += "Number Wohnraum" + i + "_Humidity       \"Luftfeuchtigkeit [%.1f %%]\"    <humidity>      " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumSensoren" + i + ":humidity\"}\n";
            }
            if(ELEC) {
                buildApartments += "Number Wohnraum" + i + "_Electricity    \"Stromzähler [%.1f kWH]\"        <energy>        " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumSensoren" + i + ":electricity\"}\n";
            }
            if(HEATER) {
                buildApartments += "Dimmer Wohnraum" + i + "_Heater         \"Heizungs Level\"                <heating>       " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumActors" + i + ":setHeater\"}\n\n";
            }
        }
        return  buildApartments;
    }

}
