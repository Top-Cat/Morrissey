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
		if (event.getMessage().startsWith("!quote")) {
			String query = "";
			if (event.getMessage().length() > 7) {
				event.getMessage().substring(7);
			}
			String sqlQuery = "SELECT * FROM quotes";
			
			if (query.length() > 0) {
				sqlQuery += " WHERE quote ILIKE '%" + query + "%' OR description ILIKE '%" + query + "%'";
			}
			
			sqlQuery += " ORDER BY RANDOM() * log(id) DESC LIMIT 1";
			Sql sql = Sql.getInstance();
			ResultSet result = sql.query(sqlQuery);
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