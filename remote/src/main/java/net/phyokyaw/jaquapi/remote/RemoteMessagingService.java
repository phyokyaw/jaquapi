package net.phyokyaw.jaquapi.remote;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("remoteMqtt")
public class RemoteMessagingService {
	private static final Logger logger = LoggerFactory.getLogger(RemoteMessagingService.class);
	
	private static final String PASSWORD = "password";
	private static final String USER_NAME = "admin";
	private final String broker = "tcp://iot.eclipse.org:1883";
	private final String clientId = "tankcontrol";
	private final MemoryPersistence persistence = new MemoryPersistence();
	private static final String topic = "/fishtank/connection";
	
	private MqttClient sampleClient;
	
	
	private final MqttCallback mqttCallback = new MqttCallback() {
		@Override
		public void connectionLost(Throwable cause) {
			// TODO Retry
		}

		@Override
		public void messageArrived(String topic, MqttMessage message)
				throws Exception {
			if (mesageUsers.containsKey(topic)) {
				mesageUsers.get(topic).messageArrived(message.toString());
			}
			if(topic.equals(topic)) {
				
			}
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
			// TODO Auto-generated method stub
		}
	};
	private Map<String, MessageListener> mesageUsers = new HashMap<String, MessageListener>();

	@PostConstruct
	private void setup() {
		connect();
	}

	private void connect() {
		try {
			sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName(USER_NAME);
			connOpts.setPassword(PASSWORD.toCharArray());
			sampleClient.setCallback(mqttCallback);
			sampleClient.connect(connOpts);
			sampleClient.subscribe(topic);
		} catch (MqttException e) {
			logger.error("Unable to connect", e);
		}
	}
	
	public void addMessageListener(String topic, MessageListener messageListener) {
		mesageUsers.put(topic, messageListener);
		try {
			sampleClient.subscribe(topic);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
