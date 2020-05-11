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
        for(int i = 1; i <= num; i++) {
            stringRules += "var Number office_group_counter" + i +"=0\n";
        }
        stringRules += "\n" +
                "rule \"> 23° Heizung aus\"\n" +
                "when\n";
        for(int i = 1; i <= num; i++) {
            stringRules += "    Item Wohnraum" + i + "_Temperature received update\n";
        }
        stringRules += "then\n";
        for(int i = 1; i <= num; i++) {
            stringRules += "\n    if(Wohnraum" + i + "_Temperature.state>23 && office_group_counter==0){\n" +
                    "        Wohnraum" + i + "_Heater.sendCommand(0)\n" +
                    "        office_group_counter=" + i + "\n" +
                    "    }\n" +
                    "    else if(Wohnraum" + i + "_Temperature.state<23 && office_group_counter==" + i + "){\n" +
                    "        office_group_counter" + i +"=0\n" +
                    "    }\n";
        }
        stringRules += "end\n";

        return stringRules;
    }

}
