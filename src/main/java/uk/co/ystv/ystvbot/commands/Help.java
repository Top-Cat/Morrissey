package uk.co.ystv.ystvbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.pircbotx.output.OutputIRC;

public class Help extends Command {
	
	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().equalsIgnoreCase("!help")) {
			OutputIRC ircout = event.getBot().sendIRC();
			
			ircout.notice(event.getUser().getNick(), "YSTV Morrissey bot - Top_Cat 2014!");
			ircout.notice(event.getUser().getNick(), "Avaliable commands:");
			
			String helpText = null;
			for (Commands cmd : Commands.values()) {
				if ((helpText = cmd.getObj().helpText()) != null) {
					ircout.notice(event.getUser().getNick(), helpText);
				}
			}
		}
	}
	
	@Override
	String helpText() {
		return "!help - such meta, so help";
	}
}