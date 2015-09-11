package net.phyokyaw.jaquapi.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.core.services.ScheduledService;

@Service("remote")
public class ControllerDataService {

	private static Logger logger = LoggerFactory
			.getLogger(ControllerDataService.class);

	@Autowired
	private ScheduledService scheduledService;

	private Map<String, List<ValueUpdateListener>> listeners = new HashMap<String, List<ValueUpdateListener>>();

	private static final String IP = "192.168.0.100";
	private static final String PORT = "8080";
	private static final String URL = "http://" + IP + ":" + PORT  + "/?";
	private static final String USER_AGENT = "Mozilla/5.0";

	private ScheduledFuture<?> updateSchedule;

	@PostConstruct
	private void setup() {
		logger.info("Start scheduling remote");
		updateSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				logger.info("Getting data");
				try {
					JSONObject json = getRemoteData("action=read");
					for (String key : listeners.keySet()) {
						if (json.has(key)) {
							String value = json.get(key).toString();
							for (ValueUpdateListener listener : listeners.get(key)) {
								listener.setValue(value);
							}
						}
					}
				} catch (IOException e) {
					logger.error("Unable to get data", e);
				}
			}
		}, 1000 * 3);
	}

	@PreDestroy
	public void shutdown() {
		updateSchedule.cancel(true);
		listeners.clear();
	}

	private JSONObject getRemoteData(String parameter) throws IOException {
		URL obj = new URL(URL + parameter);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		if (responseCode == 200) {
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				StringBuffer receivedData = new StringBuffer();
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					receivedData.append(inputLine);
				}
				return new JSONObject(receivedData.toString());
			}
		}
		throw new IOException("Unable to get data");
	}


	public String setI2cUpdate(String value) throws IOException {
		JSONObject json = getRemoteData("action=write&value=" + value);
		return json.getString("devices");
	}

	public String getPhData() throws IOException {
		JSONObject json = getRemoteData("action=ph");
		return json.get("ph").toString();
	}

	public void addValueUpdateListener(String key, ValueUpdateListener listener) {
		if (listeners.containsKey(key)) {
			listeners.get(key).add(listener);
		} else {
			List<ValueUpdateListener> updateListeners = new ArrayList<ValueUpdateListener>();
			updateListeners.add(listener);
			listeners.put(key, updateListeners);
		}

	}
}
