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
    private static int num = Integer.parseInt(getConfigurations.getConfigs("apartment", "number"));
    private static int temp = Integer.parseInt(getConfigurations.getConfigs("perApartment", "temperature"));
    private static int hum = Integer.parseInt(getConfigurations.getConfigs("perApartment", "humidity"));
    private static int heat = Integer.parseInt(getConfigurations.getConfigs("perApartment", "heater"));
    private static int elec = Integer.parseInt(getConfigurations.getConfigs("perApartment", "electricity"));

    public boolean buildItemsFile(String path) {
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(path + "apartment.items")));
            pWriter.print(groupHouse);
            pWriter.print(getGroupApartments(num));
            pWriter.print(getBuildApartments(num, temp, hum, heat, elec));
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

    private String getGroupApartments(int number){
        groupApartments += "// Group per apartment\n";
        for(int i=1; i <= number; i++){
            groupApartments += "Group Wohnraum" + i + " (House)\n";
        }
        groupApartments += "\n";
        return groupApartments;
    }

    private String getBuildApartments(int number, int temperature, int humidity, int heater, int electricity){
        for(int i=1; i <= number; i++) {
            buildApartments += "// Wohnraum" + i + "\n";
            if(temperature==1) {
                buildApartments += "Number Wohnraum" + i + "_Temperature    \"Temperatur [%.1f °C]\"          <temperature>   " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumSensoren" + i + ":temperature\"}\n\n";
            }
            if(humidity==1) {
                buildApartments += "Number Wohnraum" + i + "_Humidity       \"Luftfeuchtigkeit [%.1f %%]\"    <humidity>      " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumSensoren" + i + ":humidity\"}\n\n";
            }
            if(heater==1) {
                buildApartments += "Dimmer Wohnraum" + i + "_Heater         \"Heizungs Level\"                <heating>       " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumActors" + i + ":setHeater\"}\n\n";
            }
            if(elec==1) {
                //TODO electricity
            }
        }
        return  buildApartments;
    }

}
