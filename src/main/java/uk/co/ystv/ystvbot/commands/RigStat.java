package uk.co.ystv.ystvbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;
import uk.co.ystv.ystvbot.util.RigUpdate;

public class RigStat extends Command {
	
	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().startsWith("!rig")) {
			int amount = Integer.parseInt(RigUpdate.getRigTotal());
			int goalPerc = (int) ((amount / 7650.0)*100.0);
			int minPerc  = (int) ((amount / 3500.0)*100.0);
			event.respond("We've raised £" + amount + ", which is " + goalPerc + "% of our goal and " + minPerc + "% of our minimum." );
		}
	}
	
	@Override
	String[] helpText() {
		return new String[] {
				"!rig - Get the current status of the lighting rig YuStart project"
		};
	}
}

