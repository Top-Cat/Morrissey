package uk.co.ystv.ystvbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class Time extends ListenerAdapter<PircBotX> {
	
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().equalsIgnoreCase("!time")) {
			String time = new java.util.Date().toString();
			event.respond(event.getUser().getNick() + ": The time is now " + time);
		}
	}
	
}