package remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.phyokyaw.jaquapi.core.services.ScheduledService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("remote")
public class ControllerDataService {
	
	@Autowired
	private ScheduledService scheduledService;

	private Map<String, ValueUpdateListener> listeners = new HashMap<String, ValueUpdateListener>();

	private static final String url = "http://www.google.com/search?q=mkyong";
	private static final String USER_AGENT = "Mozilla/5.0";
	@PostConstruct
	private void setup() {
		try {
			URL obj = new URL(url);
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
					JSONObject json = new JSONObject(receivedData.toString());
					for (String key : listeners.keySet()) {
						if (!json.isNull(key)) {
							listeners.get(key).setValue(json.getString(key));
						}
					}
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean getDeviceStatus(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	public void setDeviceUpdate(int id, boolean isOn) {
		// TODO Auto-generated method stub
	}
	
	public void addValueUpdateListener(String key, ValueUpdateListener listener) {
		listeners.put(key, listener);
	}
}
