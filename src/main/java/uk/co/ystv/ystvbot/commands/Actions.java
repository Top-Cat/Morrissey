package uk.co.ystv.ystvbot.commands;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import uk.co.ystv.ystvbot.Main;

public class Actions extends Command {

	private String[] actions = new String[] { "kill", "slap", "cuddle", "reward" };
	private String[] descriptions = new String[] { "!kill <user> - Might be a bit too far", "!slaps <user> - Settle your differences the old fashioned way", "!cuddle <user> - Spread the YSTV love", "!reward <user> - Helen will reward you?" };

	private Map<String, List<String>> templates = new HashMap<String, List<String>>();
	private Map<String, Map<String, List<String>>> parts = new HashMap<String, Map<String, List<String>>>();
	private Random random = new Random();
	private Pattern pattern;

	@SuppressWarnings("unchecked")
	public Actions() {
		this.pattern = Pattern.compile("\\{[a-z_]+\\}");
		for (String action : actions) {
			Map<String, Object> config = (Map<String, Object>) Main.yaml.load(new InputStreamReader(Main.class.getResourceAsStream("/" + action + ".json")));
			this.templates.put(action, (List<String>) config.get("templates"));
			this.parts.put(action, (Map<String, List<String>>) config.get("parts"));
		}
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		for (String action : actions) {
			if (event.getMessage().startsWith("!" + action)) {
				processAction(event, action);
				break;
			}
		}
	}

	private void processAction(MessageEvent<PircBotX> event, String action) {
		if (event.getMessage().length() > action.length() + 2) {
			String target = event.getMessage().substring(action.length() + 2);

			String output = this.templates.get(action).get(this.random.nextInt(this.templates.get(action).size()));
			Matcher matcher = this.pattern.matcher(output);

			while (matcher.find()) {
				String group = matcher.group();
				group = (String) group.subSequence(1, group.length() - 1);
				String replacement = "";

				if (group.equalsIgnoreCase("user")) {
					replacement = target;
				} else {
					List<String> part = this.parts.get(action).get(group);
					replacement = part.get(this.random.nextInt(part.size()));
				}

				matcher.reset(output = matcher.replaceFirst(replacement));
			}
			event.getChannel().send().action(output);
		}
	}

	@Override
	String[] helpText() {
		return descriptions;
	}

}
