package uk.co.ystv.ystvbot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jibble.pircbot.PircBot;
import org.yaml.snakeyaml.Yaml;

public class Main extends PircBot {
	
	public static void main(String[] args) throws Exception {
		System.setProperty("http.proxyHost", "wwwcache.york.ac.uk");
		System.setProperty("http.proxyPort", "8080");
		
		new Main();
	}
	
	public Main() throws Exception {
		this.setName("Morrisey");
		this.setLogin("Morrisey");
		this.setVersion("Best Rabbit");
		this.setAutoNickChange(true);
		
		this.setVerbose(true);
		this.connect("irc.freenode.net");
		this.joinChannel("#YSTV");
	}
	
	@Override
	protected void onConnect() {
		sendRawLine("NICKSERV IDENTIFY Morrisey rubberprovideproductwide");
	}
	
	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		if (message.equalsIgnoreCase("!time")) {
			String time = new java.util.Date().toString();
			sendMessage(channel, sender + ": The time is now " + time);
		} else if (message.equalsIgnoreCase("!quote")) {
			Sql sql = Sql.getInstance();
			ResultSet result = sql.query("SELECT * FROM quotes ORDER BY RANDOM() * log(id) DESC LIMIT 1");
			try {
				result.next();
				sendMessage(channel, sender + ": '" + result.getString("quote") + "' - " + result.getString("description"));
			} catch (SQLException e) {
				sendMessage(channel, sender + ": Error getting quote, sorry :(");
				e.printStackTrace();
			}
		} else if (message.equalsIgnoreCase("!weather")) {
			sendMessage(channel, sender + ": " + getWeather());
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
