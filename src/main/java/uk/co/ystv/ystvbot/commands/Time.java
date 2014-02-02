package uk.co.ystv.ystvbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Time extends ListenerAdapter<PircBotX> {
	
	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().equalsIgnoreCase("!time")) {
			String time = new java.util.Date().toString();
			event.respond("The time is now " + time);
		}
	}
	
}