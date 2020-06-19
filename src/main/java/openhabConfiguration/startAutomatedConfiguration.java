package openhabConfiguration;

import java.io.IOException;
import java.util.logging.Logger;

public class startAutomatedConfiguration {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String args[]) throws IOException {

        if(smbSendFiles.sendFiles()){
            logger.info("Files are transfered to " + getConfigurations.getConfigs("properties", "conf_folder"));
        } else {
            logger.info("File transfer Failed!");
        }

        if(sshRestartOpenhab.sshConnectionExecute()) {
            logger.info("openHAB restart successfull");
        } else {
            logger.info("openHAB restart Failed!");
        }

    }

}
