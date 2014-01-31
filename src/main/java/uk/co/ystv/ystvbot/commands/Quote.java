package uk.co.ystv.ystvbot.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import uk.co.ystv.ystvbot.Sql;

public class Quote extends ListenerAdapter<PircBotX> {
	
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().equalsIgnoreCase("!quote")) {
			Sql sql = Sql.getInstance();
			ResultSet result = sql.query("SELECT * FROM quotes ORDER BY RANDOM() * log(id) DESC LIMIT 1");
			try {
				result.next();
				event.respond("'" + result.getString("quote") + "' - " + result.getString("description"));
			} catch (SQLException e) {
				event.respond("Error getting quote, sorry :(");
				e.printStackTrace();
			}
		}
	}
	
}