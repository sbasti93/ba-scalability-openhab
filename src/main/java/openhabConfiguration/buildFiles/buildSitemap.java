package openhabConfiguration.buildFiles;

import openhabConfiguration.getConfigurations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class buildSitemap {

    private static String stringSitemap = "";
    private static String stringLables = "";
    private static int num = Integer.parseInt(getConfigurations.getConfigs("apartment", "number"));

    public boolean buildSitemapFile(String path) {
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(path + "apartment.sitemap")));
            pWriter.print(buildSitemap(num));
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

    private String buildSitemap(int num) {
        stringSitemap += "sitemap wohnraeume label=\"Wohnräume\" {";
        stringSitemap += buildLables(num);
        stringSitemap += "}";
        return stringSitemap;
    }

    private String buildLables(int num) {
        for(int i = 1; i <= num; i++) {
            stringLables += "\n     Frame label=\"Wohnraum " + i + "\"{\n" +
                    "        Group item=Wohnraum" + i + "\n" +
                    "    }\n";
        }
        return stringLables;
    }
}
