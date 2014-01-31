package uk.co.ystv.ystvbot;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.SASLCapHandler;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import uk.co.ystv.ystvbot.commands.Commands;

public class Main extends ListenerAdapter<PircBotX> {
	
	public static void main(String[] args) throws Exception {
		System.setProperty("http.proxyHost", "wwwcache.york.ac.uk");
		System.setProperty("http.proxyPort", "8080");
		
		Configuration.Builder<PircBotX> builder = new Configuration.Builder<PircBotX>()
				.setName("Morrisey")
				.setLogin("Morrisey")
				.setVersion("Best Rabbit")
				.setAutoNickChange(true)
				.setCapEnabled(true)
				.addCapHandler(new SASLCapHandler("Morrisey", "rubberprovideproductwide"))
				.addListener(new Main())
				.setServerHostname("irc.freenode.net")
				.addAutoJoinChannel("#YSTV");
		
		for (ListenerAdapter<PircBotX> listener : Commands.listeners) {
			builder.addListener(listener);
		}
		
		new PircBotX(builder.buildConfiguration()).startBot();
	}
	
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		if (!event.getBot().getNick().equals("Morrisey")) {
			event.getBot().sendRaw().rawLineNow("NICK Morrisey");
		}
	}

}
