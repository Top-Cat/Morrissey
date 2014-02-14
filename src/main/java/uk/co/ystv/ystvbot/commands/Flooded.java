package uk.co.ystv.ystvbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Flooded extends Command {

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().startsWith("!flooded")) {
			event.respond("no");
		}
	}

	@Override
	String[] helpText() {
		return new String[] {
				"!flooded - Has YSTV flooded yet?"
		};
	}

}
