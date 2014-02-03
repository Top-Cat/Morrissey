package uk.co.ystv.ystvbot;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.SASLCapHandler;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import uk.co.ystv.ystvbot.commands.Commands;

public class Main extends ListenerAdapter<PircBotX> {

	public static PircBotX bot;

	public static void main(String[] args) throws Exception {
		System.setProperty("http.proxyHost", "wwwcache.york.ac.uk");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("https.proxyHost", "wwwcache.york.ac.uk");
		System.setProperty("https.proxyPort", "8080");

		Configuration.Builder<PircBotX> builder = new Configuration.Builder<PircBotX>().setName("Morrisey").setRealName("Best Rabbit").setLogin("Morrisey").setVersion("Best Rabbit").setAutoNickChange(true).setCapEnabled(true).addCapHandler(new SASLCapHandler("Morrisey", "rubberprovideproductwide")).addListener(new Main()).setServerHostname("irc.freenode.net").addAutoJoinChannel("#YSTV").setAutoReconnect(true);

		for (ListenerAdapter<PircBotX> listener : Commands.listeners) {
			builder.addListener(listener);
		}

		Main.bot = new PircBotX(builder.buildConfiguration());
		Main.bot.startBot();
	}

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (!event.getBot().getNick().equals("Morrisey")) {
			event.getBot().sendRaw().rawLineNow("NICK Morrisey");
		}
	}

}
