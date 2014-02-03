package uk.co.ystv.ystvbot.commands;

import java.util.Random;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Coin extends Command {

	Random random = new Random();

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {
		if (event.getMessage().startsWith("!coin")) {
			int num = 1;
			if (event.getMessage().length() > 6) {
				try {
					num = Integer.parseInt(event.getMessage().substring(6));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}

			int heads = 0; // Better way to calculate this? Probably not for
							// small num
			for (int i = 0; i < num; i++) {
				heads += this.random.nextInt(2);
			}

			if (num == 1) {
				event.respond(String.format("Flips a coin and gets %s", heads == 1 ? "heads" : "tails"));
			} else if (num < 1) {
				event.respond("Makes a coin flipping motion with its paws");
			} else {
				event.respond(String.format("Flips %d coins and gets %d heads and %d tails", num, heads, num - heads));
			}
		}
	}
	
	@Override
	String helpText() {
		return "!coin [number] - Since the committee frowns upon duels, this is the next best way to settle your disputes!";
	}

}
