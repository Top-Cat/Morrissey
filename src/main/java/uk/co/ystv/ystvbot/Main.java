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
import uk.co.ystv.ystvbot.util.Streamwatch;

public class Main extends Command {

	public static Yaml yaml = new Yaml();
	public static PircBotX bot;
	public static Streamwatch streamWatch = new Streamwatch();
	private final static String name = "Morrissey";
	static Map<String, Map<String, String>> logins;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		Main.logins = (Map<String, Map<String, String>>) Main.yaml.load(new InputStreamReader(Main.class.getResourceAsStream("/login.json")));

		Configuration.Builder<PircBotX> builder = new Configuration.Builder<PircBotX>()
				.setName(name)
				.setRealName("Best Rabbit")
				.setLogin(name)
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

		new Thread() {
			public void run() {
				while (true) {
					try {
						Main.bot.startBot();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		streamWatch.start();
	}

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (!event.getBot().getNick().equals(name)) {
			event.getBot().sendRaw().rawLineNow("NICK " + name);
		}
	}

}
