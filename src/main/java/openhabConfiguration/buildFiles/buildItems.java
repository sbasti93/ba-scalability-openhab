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
    private static boolean CO2 = Boolean.parseBoolean(getConfigurations.getConfigs("perApartment", "co2"));
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
            buildApartments +=
                    "\n// Wohnraum" + i + "\n" +

                    "Number Wohnraum" + i + "_Temperature    \"Temperatur [%.1f °C]\"                               <temperature>   " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumSensoren" + i + ":temperature\"}\n" +

                    "Number Wohnraum" + i + "_Humidity       \"Luftfeuchtigkeit [%.1f %%]\"                         <humidity>      " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumSensoren" + i + ":humidity\"}\n" +

                    "Number Wohnraum" + i + "_Electricity    \"Stromzähler [%.1f kWH]\"                             <energy>        " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumSensoren" + i + ":electricity\"}\n" +

                    "Number Wohnraum" + i + "_CO2            \"Kohlsenstoffdioxidgehalt [%.1f ppm]\"                <carbondioxide>       " +
                        "(House, Wohnraum" + i + ")  {channel=\"mqtt:topic:myWohnraumBroker:wohnraumSensoren" + i + ":kohlenstoffdioxid\"}\n" +

                    "Number Wohnraum" + i + "_Cost           \"Stromkosten [%.2f €]\"                               <price>             " +
                        "(House, Wohnraum" + i + ")  \n" +

                    "String Wohnraum" + i + "_Fire           \"Feueralarm: [%s]\"                                   <siren>             " +
                        "(House, Wohnraum" + i + ")\n\n";
        }
        return  buildApartments;
    }

}
