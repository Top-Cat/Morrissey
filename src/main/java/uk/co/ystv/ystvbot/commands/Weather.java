package uk.co.ystv.ystvbot.commands;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.yaml.snakeyaml.Yaml;

public class Weather extends Command {

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().equalsIgnoreCase("!weather")) {
			event.respond(this.getWeather());
		}
	}

	@SuppressWarnings("unchecked")
	private String getWeather() {
		try {
			Yaml yaml = new Yaml();
			Map<String, Object> map = (Map<String, Object>) yaml.load(new URL("http://api.openweathermap.org/data/2.5/weather?q=York,UK&units=metric").openStream());
			List<Object> list = (List<Object>) map.get("weather");
			Map<String, Object> main = (Map<String, Object>) map.get("main");
			Map<String, Object> wind = (Map<String, Object>) map.get("wind");
			map = (Map<String, Object>) list.get(0);
			String[] dirs = new String[] { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };
			String wind_direction = dirs[((Integer) wind.get("deg") + 22) / 45 % 8];

			return map.get("main") + ", Wind: " + wind.get("speed") + "m/s (" + wind_direction + "), Temperature: " + main.get("temp") + "C (Min: " + main.get("temp_min") + "C, Max: " + main.get("temp_max") + "C)";
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Unknown";
	}
	
	@Override
	String[] helpText() {
		return new String[] {
				"!weather - Looks outside so you don't have to!"
		};
	}

}
