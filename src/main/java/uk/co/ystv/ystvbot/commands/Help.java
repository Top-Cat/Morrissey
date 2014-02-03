package uk.co.ystv.ystvbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Help extends ListenerAdapter<PircBotX> {
	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().equalsIgnoreCase("!help")) {
			event.respond("YSTV Morrissey bot - Top_Cat 2014!"
						+ "Avaliable commands:"
						+ "!Quote - Returns a random quote!"
						+ "!Time - Mr Wolf.exe"
						+ "!Weather - Looks outside so you don't have to!"
						+ "!Coin - Since the committee frowns upon duels, this is the next best way to settle your disputes!"
						+ "!Events - Upcoming events!");
		}
	}
}