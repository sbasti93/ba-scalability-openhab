import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static org.eclipse.paho.client.mqttv3.MqttAsyncClient.generateClientId;

public class publish {

    public static void main(String args[]) throws MqttException {

        startPublishing();

    }

    public static void startPublishing() throws MqttException {
        MqttClient client = new MqttClient(
                "tcp://192.168.178.22:1883", generateClientId());

        client.connect();
        int t=0;
        while(t < 5) {
            MqttMessage messagetemp = new MqttMessage(("20."+t).getBytes());
            MqttMessage messagehum = new MqttMessage(("42."+t).getBytes());
            client.publish("apartment1/temperature/", messagetemp);
            client.publish("apartment1/humidity/", messagehum);
            t++;
        }
        client.disconnect();
    }

}
