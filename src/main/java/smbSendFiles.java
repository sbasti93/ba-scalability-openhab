import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;

public class smbSendFiles {

    private static final String USER_NAME = getConfigurations.getConfigs("properties", "user");
    private static final String PASSWORD = getConfigurations.getConfigs("properties", "pass");
    private static final String NETWORK_FOLDER = getConfigurations.getConfigs("properties", "smb_path");
    private static final String NETWORK_FOLDER_ITEMS = NETWORK_FOLDER + "items/";
    private static final String NETWORK_FOLDER_THINGS = NETWORK_FOLDER + "things/";
    private static final String NETWORK_FOLDER_RULES = NETWORK_FOLDER + "rules/";
    private static final String NETWORK_FOLDER_SITEMAPS = NETWORK_FOLDER + "sitemaps/";
    private static final String LOCAL_SAMBA_BUILD_FILES_PATH = "src/main/resources/openHabThingConfiguration/";
    private static int num = Integer.parseInt(getConfigurations.getConfigs("apartment", "number"));



    public static boolean sendFiles() {

        try {
            NtlmPasswordAuthentication auth =
                    new NtlmPasswordAuthentication(NETWORK_FOLDER, USER_NAME, PASSWORD);

            buildItems bi = new buildItems();
            buildThings bt = new buildThings();
            buildRules br = new buildRules();
            buildSitemap bs = new buildSitemap();

            if(bi.buildItemsFile(LOCAL_SAMBA_BUILD_FILES_PATH)){
                File fileSource = new File(LOCAL_SAMBA_BUILD_FILES_PATH + "apartment.items");
                SmbFile smbFileTarget = new SmbFile(NETWORK_FOLDER_ITEMS, "apartment.items");
                copyFiles(fileSource, smbFileTarget);
            }

            if(bt.buildThingsFile(LOCAL_SAMBA_BUILD_FILES_PATH)){
                File fileSource = new File(LOCAL_SAMBA_BUILD_FILES_PATH + "apartment.things");
                SmbFile smbFileTarget = new SmbFile(NETWORK_FOLDER_THINGS, "apartment.things");
                copyFiles(fileSource, smbFileTarget);
            }

            if(br.buildRulesFile(LOCAL_SAMBA_BUILD_FILES_PATH)){
                File fileSource = new File(LOCAL_SAMBA_BUILD_FILES_PATH + "apartment.rules");
                SmbFile smbFileTarget = new SmbFile(NETWORK_FOLDER_RULES, "apartment.rules");
                copyFiles(fileSource, smbFileTarget);
            }

            if(bs.buildSitemapFile(LOCAL_SAMBA_BUILD_FILES_PATH)){
                File fileSource = new File(LOCAL_SAMBA_BUILD_FILES_PATH + "apartment.sitemap");
                SmbFile smbFileTarget = new SmbFile(NETWORK_FOLDER_SITEMAPS, "apartment.sitemap");
                copyFiles(fileSource, smbFileTarget);
            }

            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }

    }

    private static boolean copyFiles(File sourceFile, SmbFile targetFile) {
        boolean successful = false;
        try{
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            SmbFileOutputStream smbFileOutputStream = new SmbFileOutputStream(targetFile);

            try{
                final byte[] b = new byte[1024*1024];
                int read = 0;
                while ((read=fileInputStream.read(b,0, b.length)) > 0){
                    smbFileOutputStream.write(b,0, read);
                }
                successful = true;
            } catch (Exception e){
                successful = false;
                e.printStackTrace();
            } finally {
                fileInputStream.close();
                smbFileOutputStream.close();
            }

        } catch (Exception e) {
            successful = false;
            e.printStackTrace();
        }
        return successful;
    }
}
