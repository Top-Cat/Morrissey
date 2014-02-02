package uk.co.ystv.ystvbot.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;

public enum Commands {
	Quote(Quote.class),
	Time(Time.class),
	Weather(Weather.class),
	Link(Link.class);
	
	public static Set<ListenerAdapter<PircBotX>> listeners = new HashSet<ListenerAdapter<PircBotX>>();
	
	private Class<? extends ListenerAdapter<PircBotX>> clazz;
	
	private Commands(Class<? extends ListenerAdapter<PircBotX>> clazz) {
		this.clazz = clazz;
	}
	
	static {
		for (Commands cmd : values()) {
			try {
				final Constructor<? extends ListenerAdapter<PircBotX>> c = cmd.clazz.getDeclaredConstructor(new Class[] {});
				listeners.add(c.newInstance());
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