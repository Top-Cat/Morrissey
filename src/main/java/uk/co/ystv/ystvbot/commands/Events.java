package uk.co.ystv.ystvbot.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;

import uk.co.ystv.ystvbot.Main;
import uk.co.ystv.ystvbot.Sql;

public class Events extends Command {

	private class EventThread extends TimerTask {

		@Override
		public void run() {
			// Look for events
			try {
				Sql sql = Sql.getInstance();
				ResultSet result = sql.query("SELECT name, start_date AT TIME ZONE 'BST' as start_date, end_time, location FROM events WHERE start_date >= now() + interval '60 minutes' AND start_date <= now() + interval '120 minutes' AND is_private = FALSE AND is_cancelled = FALSE ORDER BY start_date ASC");
				while (result.next()) {
					Main.bot.sendIRC().message("#YSTV", "Upcoming event: '" + result.getString("name") + "' in '" + result.getString("location") + "' starting at '" + result.getString("start_date") + "' and ending at '" + result.getString("end_time") + "'");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private Timer t = new Timer();
	private EventThread eventThread = new EventThread();

	public Events() {
		int period = 1 * 60 * 60 * 1000;
		this.t.schedule(this.eventThread, 30 * 1000, period);
	}

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().equalsIgnoreCase("!nextevent")) {
			Sql sql = Sql.getInstance();
			ResultSet result = sql.query("SELECT name, start_date AT TIME ZONE 'BST' as start_date, end_time, location FROM events WHERE start_date > NOW() AND is_private = FALSE AND is_cancelled = FALSE ORDER BY start_date ASC LIMIT 1");
			try {
				result.next();
				event.respond("The next event is '" + result.getString("name") + "' in '" + result.getString("location") + "' starting at '" + result.getString("start_date") + "' and ending at '" + result.getString("end_time") + "'");
			} catch (SQLException e) {
				event.respond("Error getting events, sorry :(");
				e.printStackTrace();
			}
		} else if (event.getMessage().equalsIgnoreCase("!currentevents")) {
			Sql sql = Sql.getInstance();
			ResultSet result = sql.query("SELECT name, end_time, location FROM events WHERE date_trunc('day', start_date) = date_trunc('day', now()) AND end_time > current_time AND is_private = FALSE AND is_cancelled = FALSE ORDER BY start_date ASC");
			boolean empty = true;
			try {
				while (result.next()) {
					event.respond("'" + result.getString("name") + "' in '" + result.getString("location") + "' ending at '" + result.getString("end_time") + "'");
					empty = false;
				}
				if (empty) {
					event.respond("Morrissey's slumber party in 'YSTV'");
				}
			} catch (SQLException e) {
				event.respond("Error getting events, sorry :(");
				e.printStackTrace();
			}
		}
	}

	@Override
	String[] helpText() {
		return new String[] {
				"!nextevent - Upcoming events!",
				"!currentevents - What's happening now?"
		};
	}

}
