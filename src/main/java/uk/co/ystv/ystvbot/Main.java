package uk.co.ystv.ystvbot;

import org.jibble.pircbot.PircBot;

public class Main extends PircBot {
	
	public static void main(String[] args) throws Exception {
		new Main();
	}
	
	public Main() throws Exception {
		this.setName("Morrisey");
		this.setLogin("Morrisey");
		this.setVersion("Rabbit");
		this.setAutoNickChange(true);
		
		this.setVerbose(true);
		this.connect("irc.freenode.net");
		this.joinChannel("#YSTV");
	}
	
	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		if (message.equalsIgnoreCase("!time")) {
			String time = new java.util.Date().toString();
			sendMessage(channel, sender + ": The time is now " + time);
		}
	}

}
