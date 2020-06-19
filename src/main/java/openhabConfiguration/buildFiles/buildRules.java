package openhabConfiguration.buildFiles;

import openhabConfiguration.getConfigurations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class buildRules {

    private static String stringRules = "";
    private static int num = Integer.parseInt(getConfigurations.getConfigs("apartment", "number"));

    public boolean buildRulesFile(String path) {
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(path + "apartment.rules")));
            pWriter.print(buildRules(num));
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

    private String buildRules(int num) {

        for(int i = 1; i<=num;i++){
            stringRules+=
                    "rule \"CalculateElectricityCosts" + i + "\"\n" +
                    "when\n" +
                    "    Item Wohnraum" + i + "_Electricity received update\n" +
                    "then\n" +
                    "    Wohnraum" + i + "_Cost.sendCommand(Wohnraum" + i + "_Electricity.state as DecimalType * 0.3)\n" +
                    "end\n\n";

            stringRules +=
                    "rule \"Burning-Alarm" + i + "\"\n" +
                    "when\n" +
                    "    Item Wohnraum" + i + "_CO2 received update\n" +
                    "then\n" +
                    "    if(Wohnraum" + i + "_CO2.state as DecimalType > 9 && Wohnraum" + i + "_Temperature.state as DecimalType > 35){\n" +
                    "        Wohnraum" + i + "_Fire.sendCommand(\"Feuer\")\n" +
                    "    }\n" +
                    "end\n\n";
        }


        return stringRules;
    }

}
