import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.Console;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class sshCleanCache {

    private static final Console con = System.console();

    public static boolean sshConnectionExecute() throws IOException {

        final SSHClient ssh = new SSHClient();
        ssh.addHostKeyVerifier(new PromiscuousVerifier());

        ssh.connect(getConfigurations.getConfigs("properties", "openhab_host"));
        Session session = null;

        try {
            ssh.authPassword(getConfigurations.getConfigs("properties", "openhab_login_name"),
                    getConfigurations.getConfigs("properties", "openhab_login_pwd"));
            session = ssh.startSession();
            Command cmd = session.exec(
                    "sudo /bin/systemctl status openhab2.service > isRunning.txt\n" +
                    "if  grep -q \"running\" isRunning.txt;\n" +
                    "        then\n" +
                    "                echo \"openHab2 is Running!\"\n" +
                    "                echo \"stopping openHab2...\"\n" +
                    "                sudo /bin/systemctl stop openhab2.service &\n" +
                    "                wait\n" +
                    "                echo \"openHab2 is stopped!\"\n" +
                    "                echo \"Cache is cleaning...\"\n" +
                    "                yes | sudo openhab-cli clean-cache &\n" +
                    "                wait\n" +
                    "                echo \"Cache is clean!\"\n" +
                    "                echo \"Start openHab2\"\n" +
                    "                sudo /bin/systemctl start openhab2.service &\n" +
                    "                wait\n" +
                    "                echo \"openHab2 started!\"\n" +
                    "        else\n" +
                    "                echo \"openHab2 is not running!\"\n" +
                    "                sudo /bin/systemctl start openhab2.service &\n" +
                    "                wait\n" +
                    "                echo \"Now openHab2 is started\"\n" +
                    "fi\n" +
                    "rm isRunning.txt");
            String text = IOUtils.readFully(cmd.getInputStream()).toString();
            cmd.join(5, TimeUnit.SECONDS);
            return true;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                ssh.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
