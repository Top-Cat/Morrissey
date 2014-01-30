package uk.co.ystv.ystvbot;

import org.jibble.pircbot.PircBot;

public class Main extends PircBot {
	
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		
		main.setVerbose(true);
		main.connect("irc.freenode.net");
		main.joinChannel("#YSTV");
	}
	
	public Main() {
		this.setName("Morrisey");
	}
	
	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		if (message.equalsIgnoreCase("!time")) {
			String time = new java.util.Date().toString();
			sendMessage(channel, sender + ": The time is now " + time);
		}
	}

}
