package uk.co.ystv.ystvbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;

public abstract class Command extends ListenerAdapter<PircBotX> {
	
	String[] helpText() {
		return null;
	}
	
}