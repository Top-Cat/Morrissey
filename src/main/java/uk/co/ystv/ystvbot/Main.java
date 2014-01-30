package uk.co.ystv.ystvbot;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jibble.pircbot.PircBot;

public class Main extends PircBot {
	
	public static void main(String[] args) throws Exception {
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
		sendMessage("Top_Cat", "/ns identify Morrisey rubberprovideproductwide");
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
		}
	}

}
