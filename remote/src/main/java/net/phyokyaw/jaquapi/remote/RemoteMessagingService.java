package net.phyokyaw.jaquapi.remote;

import java.util.concurrent.ScheduledFuture;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.core.util.MultiToMultiMap;

@Service("remoteMqtt")
public class RemoteMessagingService {
	private static final Logger logger = LoggerFactory.getLogger(RemoteMessagingService.class);

	private static final String PASSWORD = "password";
	private static final String USER_NAME = "admin";
	private final String broker = "tcp://127.0.0.1:61613";
	private final String clientId = "tankcontrol";
	private final MemoryPersistence persistence = new MemoryPersistence();
	private static final String FISH_TANK_CONNECTION = "/fishtank/connection";
	private ScheduledFuture<?> retryConnection = null;

	@Autowired
	private ScheduledService scheduledService;

	private MultiToMultiMap<String, MessageListener> messageListners = new MultiToMultiMap<String, MessageListener>();

	private MqttClient sampleClient;

	private final MqttCallback mqttCallback = new MqttCallback() {
		@Override
		public void connectionLost(Throwable cause) {
			logger.error("Unable to connect to server, retrying...", cause);
			connect();
		}

		@Override
		public void messageArrived(String topic, MqttMessage message)
				throws Exception {
			for (MessageListener listeners : messageListners.getValues(topic)) {
				listeners.messageArrived(topic, message.toString());
			}

			if(topic.equals(FISH_TANK_CONNECTION)) {
				int state = Integer.parseInt(message.toString());
				boolean sensorsAvailable = (state == 1);
				for (MessageListener listeners : messageListners.getAllValues()) {
					listeners.sensorDeviceConnection(sensorsAvailable);
				}
				if (!sensorsAvailable) {
					logger.error("Connection lost with sensors");
				}
			}
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
			// TODO Auto-generated method stub
		}
	};

	@PostConstruct
	private void setup() {
		connect();
	}

	private void connect() {
		retryConnection = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				try {
					logger.debug("trying to connect");
					sampleClient = new MqttClient(broker, clientId, persistence);
					MqttConnectOptions connOpts = new MqttConnectOptions();
					connOpts.setCleanSession(true);
					connOpts.setUserName(USER_NAME);
					connOpts.setPassword(PASSWORD.toCharArray());
					sampleClient.setCallback(mqttCallback);
					sampleClient.connect(connOpts);
					if (sampleClient.isConnected()) {
						logger.debug("connected, canceling retry");
						retryConnection.cancel(false);
					}
					sampleClient.subscribe(FISH_TANK_CONNECTION);
					for (String key : messageListners.getAllKeys()) {
						sampleClient.subscribe(key);
					}
				} catch (MqttException e) {
					logger.error("Unable to connect: " + e.getMessage());
				}
			}
		}, 1000 * 10); // 10s
	}

	public void addMessageListener(String topic, MessageListener messageListener) {
		messageListners.put(topic, messageListener);
		try {
			if (sampleClient != null && sampleClient.isConnected()) {
				sampleClient.subscribe(topic);
			}
		} catch (MqttException e) {
			logger.error("Unable to subscribe " + topic, e);
		}
	}

	public static void main(String[] args) {
		RemoteMessagingService s = new RemoteMessagingService();
		s.setup();
	}
}
