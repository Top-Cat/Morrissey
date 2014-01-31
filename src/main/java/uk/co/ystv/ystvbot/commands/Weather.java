package uk.co.ystv.ystvbot.commands;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.yaml.snakeyaml.Yaml;

public class Weather extends ListenerAdapter<PircBotX> {
	
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().equalsIgnoreCase("!weather")) {
			event.respond(event.getUser().getNick() + getWeather());
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getWeather() {
		try {
			Yaml yaml = new Yaml();
			Map<String, Object> map = (Map<String, Object>) yaml.load(new URL("http://api.openweathermap.org/data/2.5/weather?q=York,UK").openStream());
			List<Object> list = (List<Object>) map.get("weather");
			map = (Map<String, Object>) list.get(0);
			return (String) map.get("main");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Unknown";
	}
	
}