package openhabConfiguration;

import java.io.IOException;
import java.util.logging.Logger;

public class startAutomatedConfiguration {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String args[]) throws IOException {

        if(smbSendFiles.sendFiles()){
            logger.info("Files are transfered to " + getConfigurations.getConfigs("properties", "smb_path"));
        } else {
            logger.info("File transfer Failed!");
        }

        if(openhabConfiguration.sshCleanCache.sshConnectionExecute()) {
            logger.info("Cache is cleaned up");
        } else {
            logger.info("Cleaning Cache Failed!");
        }

        if(openhabConfiguration.startCmdPublisher.startPublisher()) {
            logger.info("Publisher started!");
        } else {
            logger.info("Start Publisher Failed");
        }

    }

}
