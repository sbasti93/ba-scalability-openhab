package openhabConfiguration;

import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.utils.SmbFiles;
import openhabConfiguration.buildFiles.buildItems;
import openhabConfiguration.buildFiles.buildRules;
import openhabConfiguration.buildFiles.buildSitemap;
import openhabConfiguration.buildFiles.buildThings;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class smbSendFiles {

    private static final String USER_NAME = getConfigurations.getConfigs("properties", "openhab_login_name");
    private static final String PASSWORD = getConfigurations.getConfigs("properties", "openhab_login_pwd");
    private static final String HOST = getConfigurations.getConfigs("properties", "openhab_host");
    private static final String NETWORK_FOLDER = getConfigurations.getConfigs("properties", "conf_folder");
    private static final int HEATER_AVAILABLE = Integer.parseInt(getConfigurations.getConfigs("perApartment", "heater"));
    private static final String NETWORK_FOLDER_ITEMS = "items\\";
    private static final String NETWORK_FOLDER_THINGS = "things\\";
    private static final String NETWORK_FOLDER_RULES = "rules\\";
    private static final String NETWORK_FOLDER_SITEMAPS = "sitemaps\\";
    private static final String LOCAL_SAMBA_BUILD_FILES_PATH = "src/main/resources/openHabThingConfiguration/";

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public static boolean sendFiles() {

        try(Session session = authentication()){

            buildItems bi = new buildItems();
            buildThings bt = new buildThings();
            buildRules br = new buildRules();
            buildSitemap bs = new buildSitemap();

            if(bi.buildItemsFile(LOCAL_SAMBA_BUILD_FILES_PATH)){
                try(DiskShare shareItems = (DiskShare) Objects.requireNonNull(session).connectShare(NETWORK_FOLDER)){
                    File source = new File(LOCAL_SAMBA_BUILD_FILES_PATH + "apartment.items");
                    String destination = NETWORK_FOLDER_ITEMS + source.getName();
                    SmbFiles.copy(source, shareItems, destination, true);
                }
            }

            if(bt.buildThingsFile(LOCAL_SAMBA_BUILD_FILES_PATH)){
                try(DiskShare shareThings = (DiskShare) Objects.requireNonNull(session).connectShare(NETWORK_FOLDER)) {
                    File source = new File(LOCAL_SAMBA_BUILD_FILES_PATH + "apartment.things");
                    String destination = NETWORK_FOLDER_THINGS + source.getName();
                    SmbFiles.copy(source, shareThings, destination, true);
                }
            }

            if(HEATER_AVAILABLE==1) {
                if (br.buildRulesFile(LOCAL_SAMBA_BUILD_FILES_PATH)) {
                    try (DiskShare shareRules = (DiskShare) Objects.requireNonNull(session).connectShare(NETWORK_FOLDER)) {
                        File source = new File(LOCAL_SAMBA_BUILD_FILES_PATH + "apartment.rules");
                        String destination = NETWORK_FOLDER_RULES + source.getName();
                        SmbFiles.copy(source, shareRules, destination, true);
                    }
                }
            }

            if(bs.buildSitemapFile(LOCAL_SAMBA_BUILD_FILES_PATH)){
                try(DiskShare shareSitemap = (DiskShare) Objects.requireNonNull(session).connectShare(NETWORK_FOLDER)) {
                    File source = new File(LOCAL_SAMBA_BUILD_FILES_PATH + "apartment.sitemap");
                    String destination = NETWORK_FOLDER_SITEMAPS + source.getName();
                    SmbFiles.copy(source, shareSitemap, destination, true);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    private static Session authentication() {

        SmbConfig config = SmbConfig.builder().withTimeout(120, TimeUnit.SECONDS)
                .withTimeout(120, TimeUnit.SECONDS)
                .withSoTimeout(180, TimeUnit.SECONDS)
                .build();

        SMBClient client = new SMBClient(config);

        try {
            Connection connection = client.connect(HOST);
            AuthenticationContext ac = new AuthenticationContext(USER_NAME, PASSWORD.toCharArray(), NETWORK_FOLDER);
            Session session = connection.authenticate(ac);

            logger.info("Authentication successful!\n-> Domain: " + ac.getDomain() +
                    "\n-> Name: " + ac.getUsername() + "\n-> Session-ID: " + session.getSessionId());

            return session;

        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Authentication FAILED!");
            return null;
        }

    }

}



