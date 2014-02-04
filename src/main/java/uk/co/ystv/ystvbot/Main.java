package uk.co.ystv.ystvbot;

import java.io.InputStreamReader;
import java.util.Map;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.SASLCapHandler;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.yaml.snakeyaml.Yaml;

import uk.co.ystv.ystvbot.commands.Command;
import uk.co.ystv.ystvbot.commands.Commands;

public class Main extends Command {

	public static Yaml yaml = new Yaml();
	public static PircBotX bot;
	static Map<String, Map<String, String>> logins;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		System.setProperty("http.proxyHost", "wwwcache.york.ac.uk");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("https.proxyHost", "wwwcache.york.ac.uk");
		System.setProperty("https.proxyPort", "8080");

		Main.logins = (Map<String, Map<String, String>>) Main.yaml.load(new InputStreamReader(Main.class.getResourceAsStream("/login.json")));

		Configuration.Builder<PircBotX> builder = new Configuration.Builder<PircBotX>()
				.setName("Morrisey")
				.setRealName("Best Rabbit")
				.setLogin("Morrisey")
				.setVersion("Best Rabbit")
				.setAutoNickChange(true)
				.setCapEnabled(true)
				.addCapHandler(new SASLCapHandler(Main.logins.get("nickserv").get("user"), Main.logins.get("nickserv").get("pass")))
				.addListener(new Main())
				.setServerHostname("chat.freenode.net")
				.addAutoJoinChannel("#YSTV")
				.setMessageDelay(0)
				.setAutoReconnect(true);

		for (Command listener : Commands.listeners) {
			builder.addListener(listener);
		}

		Main.bot = new PircBotX(builder.buildConfiguration());
		Main.bot.startBot();
	}

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (!event.getBot().getNick().equals(Main.logins.get("nickserv").get("user"))) {
			event.getBot().sendRaw().rawLineNow("NICK " + Main.logins.get("nickserv").get("user"));
		}
	}

}
