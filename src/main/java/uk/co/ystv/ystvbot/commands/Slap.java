package uk.co.ystv.ystvbot.commands;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import uk.co.ystv.ystvbot.Main;

public class Slap extends Command {

	private List<String> templates;
	private Map<String, List<String>> parts;
	private Random random = new Random();
	private Pattern pattern;

	@SuppressWarnings("unchecked")
	public Slap() {
		this.pattern = Pattern.compile("\\{[a-z_]+\\}");
		Map<String, Object> config = (Map<String, Object>) Main.yaml.load(new InputStreamReader(Main.class.getResourceAsStream("/slaps.json")));
		this.templates = (List<String>) config.get("templates");
		this.parts = (Map<String, List<String>>) config.get("parts");
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().startsWith("!slap") && event.getMessage().length() > 6) {
			String target = event.getMessage().substring(6);

			String output = this.templates.get(this.random.nextInt(this.templates.size()));
			Matcher matcher = this.pattern.matcher(output);

			while (matcher.find()) {
				String group = matcher.group();
				group = (String) group.subSequence(1, group.length() - 1);
				String replacement = "";

				if (group.equalsIgnoreCase("user")) {
					replacement = target;
				} else {
					List<String> part = this.parts.get(group);
					replacement = part.get(this.random.nextInt(part.size()));
				}

				matcher.reset(output = matcher.replaceFirst(replacement));
			}
			event.getChannel().send().action(output);
		}
	}

	@Override
	String[] helpText() {
		return new String[] { "!slap <user> - Settle your differences the old fashioned way" };
	}

}
