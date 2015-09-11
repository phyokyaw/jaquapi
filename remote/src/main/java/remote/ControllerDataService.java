package remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.phyokyaw.jaquapi.core.services.ScheduledService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	
@Service("remote")
public class ControllerDataService {

	private static Logger logger = LoggerFactory
			.getLogger(ControllerDataService.class);

	@Autowired
	private ScheduledService scheduledService;

	private Map<String, ValueUpdateListener> listeners = new HashMap<String, ValueUpdateListener>();

	private static final String IP = "192.168.0.100";
	private static final String URL = "http://" + IP + "/?action=";
	private static final String USER_AGENT = "Mozilla/5.0";

	private ScheduledFuture<?> updateSchedule;

	@PostConstruct
	private void setup() {
		logger.info("Start scheduling remote");
		updateSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				try {
					JSONObject json = getRemoteData("action=read");
					for (String key : listeners.keySet()) {
						if (!json.isNull(key)) {
							listeners.get(key).setValue(json.getString(key));
						}
					}
				} catch (IOException e) {
					logger.error("Unable to get data", e);
				}
			}
		}, 1000);
	}
	
	@PreDestroy
	public void shutdown() {
		updateSchedule.cancel(true);
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
			try (BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()))) {
				String inputLine;
				StringBuffer receivedData = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					receivedData.append(inputLine);
				}
				return new JSONObject(receivedData.toString());
			}
		}
		throw new IOException("Cant get data");
	}


	public String setI2cUpdate(String value) throws IOException {
		JSONObject json = getRemoteData("action=set&value=" + value);
		return json.getString("i2c");
	}
	
	public String getPhData() throws IOException {
		JSONObject json = getRemoteData("action=ph");
		return json.getString("ph");
	}

	public void addValueUpdateListener(String key, ValueUpdateListener listener) {
		listeners.put(key, listener);
	}
}
