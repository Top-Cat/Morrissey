package uk.co.ystv.ystvbot.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import uk.co.ystv.ystvbot.util.GetTitle;

public class Link extends ListenerAdapter<PircBotX> {
	
	private Pattern pattern;
	
	public Link() {
		pattern = Pattern.compile("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[\\-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9\\.\\-]+|(?:www\\.|[\\-;:&=\\+\\$,\\w]+@)[A-Za-z0-9\\.\\-]+)((?:\\/[\\+~%\\/\\.\\w\\-_]*)?\\??(?:[\\-\\+=&;%@\\.\\w_]*)#?(?:[\\.\\!\\/\\\\\\w]*))?)");
	}
	
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		Matcher matcher = pattern.matcher(event.getMessage());
		
		while (matcher.find()) {
			String[] title = GetTitle.get(matcher.group());
			if (title != null) {
				event.respond(title[1] + " - " + title[0]);
			}
		}
	}
	
}