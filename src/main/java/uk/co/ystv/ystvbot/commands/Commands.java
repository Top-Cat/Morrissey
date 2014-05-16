package uk.co.ystv.ystvbot.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public enum Commands {
	Quote(Quote.class),
	Time(Time.class),
	Weather(Weather.class),
	Link(Link.class),
	Coin(Coin.class),
	Events(Events.class),
	Help(Help.class),
	Kill(Actions.class),
	Flooded(Flooded.class);

	public static Set<Command> listeners = new HashSet<Command>();

	private Class<? extends Command> clazz;
	@Getter @Setter private Command obj;

	private Commands(Class<? extends Command> clazz) {
		this.clazz = clazz;
	}

	static {
		for (Commands cmd : Commands.values()) {
			try {
				final Constructor<? extends Command> c = cmd.clazz.getDeclaredConstructor(new Class[] {});
				Command obj = c.newInstance();
				Commands.listeners.add(obj);
				cmd.setObj(obj);
			} catch (final NoSuchMethodException e) {
				e.printStackTrace();
			} catch (final SecurityException e) {
				e.printStackTrace();
			} catch (final InstantiationException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			} catch (final IllegalArgumentException e) {
				e.printStackTrace();
			} catch (final InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
