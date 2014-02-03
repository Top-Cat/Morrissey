package uk.co.ystv.ystvbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Time extends Command {

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().equalsIgnoreCase("!time")) {
			String time = new java.util.Date().toString();
			event.respond("The time is now " + time);
		}
	}
	
	@Override
	String helpText() {
		return "!time - Mr Wolf.exe";
	}

}
