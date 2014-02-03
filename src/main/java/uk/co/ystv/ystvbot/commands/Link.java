package uk.co.ystv.ystvbot.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;

import uk.co.ystv.ystvbot.util.GetTitle;

public class Link extends Command {

	private Pattern pattern;

	public Link() {
		this.pattern = Pattern.compile("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[\\-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9\\.\\-]+|(?:www\\.|[\\-;:&=\\+\\$,\\w]+@)[A-Za-z0-9\\.\\-]+)((?:\\/[\\+~%\\/\\.\\w\\-_]*)?\\??(?:[\\-\\+=&;%@\\.\\w_]*)#?(?:[\\.\\!\\/\\\\\\w]*))?)");
	}

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		Matcher matcher = this.pattern.matcher(event.getMessage());

		while (matcher.find()) {
			String[] title = GetTitle.get(matcher.group());
			if (title != null) {
				event.respond(title[1] + " - " + title[0]);
			}
		}
	}

}
