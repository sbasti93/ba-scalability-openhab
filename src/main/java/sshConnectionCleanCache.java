import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.Console;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class sshConnectionCleanCache {

    private static final Console con = System.console();
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String args[]) throws IOException {

        sshConnectionExecute();

    }

    //TODO File für das Cache-Cleaning abfragen! -> Dieses File nochmal überarbeiten
    public static String sshConnectionExecute() throws IOException {

        final SSHClient ssh = new SSHClient();
        ssh.addHostKeyVerifier(new PromiscuousVerifier());

        ssh.connect("192.168.178.22");
        Session session = null;

        try {
            ssh.authPassword(getConfigurations.getConfigs("properties", "openhab_login_name"),
                    getConfigurations.getConfigs("properties", "openhab_login_pwd"));
            session = ssh.startSession();
            Command status1 = session.exec("./clearCache.sh");
            String text = IOUtils.readFully(status1.getInputStream()).toString();
            status1.join(5, TimeUnit.SECONDS);
            logger.info(text);
            return text;
        } finally {
            try {
                if(session != null) {
                    session.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ssh.disconnect();
        }

    }

}
