package uk.co.ystv.ystvbot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.SASLCapHandler;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.yaml.snakeyaml.Yaml;

import uk.co.ystv.ystvbot.commands.Command;
import uk.co.ystv.ystvbot.commands.Commands;
import uk.co.ystv.ystvbot.util.Streamwatch;

public class Main extends Command {

	public static Yaml yaml = new Yaml();
	public static PircBotX bot;
	public static Streamwatch streamwatch = new Streamwatch();
	private final static String name = "Morrissey";
	static Map<String, Map<String, String>> logins;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		System.setProperty("http.proxyHost", "wwwcache.york.ac.uk");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("https.proxyHost", "wwwcache.york.ac.uk");
		System.setProperty("https.proxyPort", "8080");

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
		
		
		Thread thread1 = new Thread () {
			  public void run () {
			    streamwatch.run();
			  }
			};
			Thread thread2 = new Thread () {
			  public void run () {
				  try {
					Main.bot.startBot();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (IrcException e) {
					e.printStackTrace();
				}
			  }
			};
			thread1.start();
			thread2.start();
	}

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (!event.getBot().getNick().equals(name)) {
			event.getBot().sendRaw().rawLineNow("NICK " + name);
		}
	}

}
