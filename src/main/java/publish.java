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
                "myWohnraumBroker:192.168.178.22", generateClientId());

        client.connect();
        client.publish("apartment1/temperatur/", "20.1".getBytes(), 2, true);

        client.disconnect();
    }

}
