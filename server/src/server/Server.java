package server;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23017Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
@SuppressWarnings("restriction")
public class Server {
	private enum Response {ERROR, OK};
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("/service", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
        	Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
        	String response = Response.ERROR.toString();
        	if (params.containsKey("temp")) {
        		response = getTemp();
        	} else if (params.containsKey("ph")) {
        		response = getPh();
        	} else if (params.containsKey("dev")) {
        		if (params.get("dev").equals("set") && params.containsKey("value")) {
        			response = setI2c(params.get("value"));
        		} else if (params.get("dev").equals("get")) {
        			response = getI2c();
        		}
        	} else if (params.containsKey("sensor")) {
        		response = getSensor(params.get("sensor"));
        	}
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }
    

	private static String getSensor(String string) {
		
		return "1";
	}

	private static String getI2c() {
		try {
			MCP23017GpioProvider f = new MCP23017GpioProvider(0x20, 0x01);
			f.setMode(MCP23017Pin.GPIO_A0, PinMode.DIGITAL_OUTPUT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ERROR.toString();
	}

	private static String setI2c(String string) {
		// TODO Auto-generated method stub
		return "OK";
	}

	private static String getPh() {
		// TODO Auto-generated method stub
		return "8";
	}

	private static String getTemp() {
		try {
			List<String> lines = Files.readAllLines(Paths.get("/sys/bus/w1/devices/28-031466113fff/w1_slave"));
			Pattern pattern = Pattern.compile(".*t=(\\d+)");
			Matcher matcher = pattern.matcher(lines.get(1));
			if (matcher.find()) {
				return Double.toString(Double.parseDouble(matcher.group(1)) / 1000d);
			}
			return Response.ERROR.toString();
		} catch (IOException e) {
			return Response.ERROR.toString();
		}
	}
}
